package by.maiseichyk.finalproject.service.impl;

import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.dao.Transaction;
import by.maiseichyk.finalproject.dao.impl.BetDaoImpl;
import by.maiseichyk.finalproject.dao.impl.SportEventDaoImpl;
import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.*;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.SportEventService;
import by.maiseichyk.finalproject.util.EventResultGenerator;
import by.maiseichyk.finalproject.util.validator.impl.SportEventValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class SportEventServiceImpl implements SportEventService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SportEventServiceImpl instance = new SportEventServiceImpl();
    public static final int LOST = -1;
    public static final int DRAWN = 0;
    public static final int WIN = 1;

    private SportEventServiceImpl() {
    }

    public static SportEventServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean addEvent(Map<String, String> eventData) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        SportEventValidatorImpl eventValidator = SportEventValidatorImpl.getInstance();
        if (eventValidator.checkEventData(eventData)) {
            SportEvent sportEvent = new SportEvent.SportEventBuilder()
                    .setEventType(SportEventType.valueOf(eventData.get(EVENT_TYPE)))
                    .setFirstTeam(eventData.get(RequestParameter.FIRST_TEAM_NAME))
                    .setFirstTeamRatio(BigDecimal.valueOf(Long.parseLong(eventData.get(FIRST_TEAM_RATIO))))
                    .setSecondTeam(eventData.get(RequestParameter.SECOND_TEAM_NAME))
                    .setSecondTeamRatio(BigDecimal.valueOf(Long.parseLong(eventData.get(SECOND_TEAM_RATIO))))
                    .setEventDate(LocalDate.parse(eventData.get(EVENT_DATE)))
                    .build();
            try {
                if (eventDao.insert(sportEvent)) {
                    return true;
                }
            } catch (DaoException e) {
                LOGGER.error("Exception while adding event: " + e);
                throw new ServiceException(e);
            } finally {
                eventDao.closeConnection();
            }
        }
        return false;
    }

    @Override
    public List<Bet> insertEventResult(SportEvent sportEvent) throws ServiceException {
        String firstTeam = sportEvent.getFirstTeam();
        String secondTeam = sportEvent.getSecondTeam();
        Map<String, Integer> eventResult = EventResultGenerator.generateResult(firstTeam, secondTeam);
        Integer firstTeamResult = eventResult.get(firstTeam);
        Integer secondTeamResult = eventResult.get(secondTeam);
        Transaction transaction = Transaction.getInstance();
        BetDaoImpl betDao = new BetDaoImpl(true);
        SportEventDaoImpl eventDao = new SportEventDaoImpl(true);
        BetServiceImpl betService = BetServiceImpl.getInstance();
        List<Bet> betsForEvent = new ArrayList<>();
        try {
            transaction.begin(eventDao, betDao);
            List<Bet> betsForFirstTeam = betDao.findProcessingBetsForProvidedTeamByEventId(sportEvent.getId(), firstTeam);
            List<Bet> betsForSecondTeam = betDao.findProcessingBetsForProvidedTeamByEventId(sportEvent.getId(), secondTeam);
            LOGGER.info(firstTeamResult.compareTo(secondTeamResult));
            switch (firstTeamResult.compareTo(secondTeamResult)) {
                case LOST -> {
                    for (Bet bet : betsForFirstTeam) {
                        betService.calculateBetSummary(bet, LOST);
                    }
                    for (Bet bet : betsForSecondTeam) {
                        betService.calculateBetSummary(bet, WIN);
                    }
                }
                case DRAWN -> {
                    for (Bet bet : betsForFirstTeam) {
                        betService.calculateBetSummary(bet, DRAWN);
                    }
                    for (Bet bet : betsForSecondTeam) {
                        betService.calculateBetSummary(bet, DRAWN);
                    }
                }
                case WIN -> {
                    for (Bet bet : betsForFirstTeam) {
                        betService.calculateBetSummary(bet, WIN);
                    }
                    for (Bet bet : betsForSecondTeam) {
                        betService.calculateBetSummary(bet, LOST);
                    }
                }
            }
            betsForEvent.addAll(betsForSecondTeam);
            betsForEvent.addAll(betsForFirstTeam);
            if (betDao.updateBetStatus(betsForEvent)) {
                if (eventDao.delete(sportEvent)) {
                    if (eventDao.insertPastSportEvent(sportEvent, eventResult.values().toString())) {
                        transaction.commit();
                    }
                }
            }
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException exception) {
                LOGGER.error("Exception while rollback transaction: " + exception);
            }
            LOGGER.error("Exception while releasing event: " + e);
            throw new ServiceException("Exception while releasing event: ", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException daoException) {
                LOGGER.error("Error has occurred while ending transaction for registering user: " + daoException);
            }
        }
        return betsForEvent;
    }

    @Override
    public Optional<SportEvent> findSportEventById(String eventId) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        try {
            Optional<SportEvent> sportEvent = eventDao.findSportEventInDb(eventId);
            if (sportEvent.isPresent()) {
                return sportEvent;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while finding event by id: " + e);
            throw new ServiceException("Exception while finding event by id: ", e);
        } finally {
            eventDao.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public List<SportEvent> findSportEventByTeamsName(String firstTeamName, String secondTeamName) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        SportEventValidatorImpl eventValidator = SportEventValidatorImpl.getInstance();
        List<SportEvent> sportEventList = new ArrayList<>();
        try {
            if (eventValidator.checkTeamName(firstTeamName) && eventValidator.checkTeamName(secondTeamName)) {
                sportEventList = eventDao.findSportEventByTeamsName(firstTeamName, secondTeamName);
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while finding events by names. " + e);
            throw new ServiceException("Exception while finding events by names. ", e);
        }
        return sportEventList;
    }

    @Override
    public List<SportEvent> findAllOngoingEvents() throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        List<SportEvent> ongoingEvents;
        try {
            ongoingEvents = eventDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Exception while finding events: " + e);
            throw new ServiceException("Exception while finding events: ", e);
        } finally {
            eventDao.closeConnection();
        }
        return ongoingEvents;
    }

    @Override
    public List<SportEvent> findAllPastEvents() throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        List<SportEvent> pastEvents;
        try {
            pastEvents = eventDao.findAllPastEvents();
        } catch (DaoException e) {
            LOGGER.error("Exception while finding past events: " + e);
            throw new ServiceException("Exception while finding past events: ", e);
        } finally {
            eventDao.closeConnection();
        }
        return pastEvents;
    }

    @Override
    public boolean deleteSportEvent(String eventId) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(true);
        BetDaoImpl betDao = new BetDaoImpl(true);
        UserDaoImpl userDao = new UserDaoImpl(true);
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Transaction transaction = Transaction.getInstance();
        try {
            transaction.begin(betDao, eventDao, userDao);
            Optional<SportEvent> sportEvent = eventDao.findSportEventInDb(eventId);
            if (sportEvent.isPresent()) {
                List<Bet> bets = betDao.findAllBetsByEventId(sportEvent.get().getId());
                List<String> logins = new ArrayList<>();
                Map<String, BigDecimal> loginsAndBetAmount = new HashMap<>();
                for (Bet bet : bets) {
                    String userLogin = bet.getUserLogin();
                    BigDecimal betAmount = bet.getBetAmount();
                    loginsAndBetAmount.put(userLogin, betAmount);
                    bet.setBetStatus(BetStatus.DELETED);
                }
                List<User> users = userService.findUsers(logins);
                for (User user : users) {
                    BigDecimal betAmount = loginsAndBetAmount.get(user.getLogin());
                    BigDecimal updatedBalance = user.getBalance().add(betAmount);
                    user.setBalance(updatedBalance);
                }
                if (userDao.updateUsersBalance(users)) {
                    if (betDao.updateBetStatus(bets)) {
                        if (eventDao.delete(sportEvent.get())) {
                            transaction.commit();
                            return true;
                        }
                    }
                }
            }
            transaction.rollback();
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                LOGGER.error("Exception while rollbacking transaction: " + ex);
            }
            LOGGER.error("Exception while deleting event: " + e);
            throw new ServiceException("Exception while deleting event: ", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                LOGGER.error("Exception while ending transaction: " + e);
            }
        }
        return false;
    }

    @Override
    public boolean updateFirstTeamName(String eventId, String changeTo) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        SportEventValidatorImpl eventValidator = SportEventValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (eventValidator.checkTeamName(changeTo)) {
                if (eventDao.updateFirstTeamName(eventId, changeTo)) {
                    status = true;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating team name: " + e);
            throw new ServiceException("Exception while updating team name: ", e);
        } finally {
            eventDao.closeConnection();
        }
        return status;
    }

    @Override
    public boolean updateSecondTeamName(String eventId, String changeTo) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        SportEventValidatorImpl eventValidator = SportEventValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (eventValidator.checkTeamName(changeTo)) {
                if (eventDao.updateSecondTeamName(eventId, changeTo)) {
                    status = true;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating team name: " + e);
            throw new ServiceException("Exception while updating team name: ", e);
        } finally {
            eventDao.closeConnection();
        }
        return status;
    }

    @Override
    public boolean updateFirstTeamRatio(String eventId, BigDecimal changeTo) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        SportEventValidatorImpl eventValidator = SportEventValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (eventValidator.checkTeamRatio(changeTo.toString())) {
                if (eventDao.updateFirstTeamRatio(eventId, changeTo)) {
                    status = true;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating team ratio: " + e);
            throw new ServiceException("Exception while updating team ratio: ", e);
        } finally {
            eventDao.closeConnection();
        }
        return status;
    }

    @Override
    public boolean updateSecondTeamRatio(String eventId, BigDecimal changeTo) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        SportEventValidatorImpl eventValidator = SportEventValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (eventValidator.checkTeamRatio(changeTo.toString())) {
                if (eventDao.updateSecondTeamRatio(eventId, changeTo)) {
                    status = true;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating team ratio: " + e);
            throw new ServiceException("Exception while updating team ratio: ", e);
        } finally {
            eventDao.closeConnection();
        }
        return status;
    }

    @Override
    public boolean updateEventDate(String eventId, LocalDate date) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        SportEventValidatorImpl eventValidator = SportEventValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (eventValidator.checkEventDate(date.toString())) {
                if (eventDao.updateEventDate(eventId, date)) {
                    status = true;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating event date: " + e);
            throw new ServiceException("Exception while updating event date: ", e);
        } finally {
            eventDao.closeConnection();
        }
        return status;
    }

    @Override
    public boolean updateEventType(String eventId, SportEventType eventType) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        SportEventValidatorImpl eventValidator = SportEventValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (eventValidator.checkEventType(eventType.toString())) {
                if (eventDao.updateEventType(eventId, eventType)) {
                    status = true;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating event type: " + e);
            throw new ServiceException("Exception while updating event type: ", e);
        } finally {
            eventDao.closeConnection();
        }
        return status;
    }
}
