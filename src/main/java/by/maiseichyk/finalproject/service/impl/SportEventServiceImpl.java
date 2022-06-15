package by.maiseichyk.finalproject.service.impl;

import by.maiseichyk.finalproject.dao.Transaction;
import by.maiseichyk.finalproject.dao.impl.BetDaoImpl;
import by.maiseichyk.finalproject.dao.impl.SportEventDaoImpl;
import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.*;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.SportEventService;
import by.maiseichyk.finalproject.util.EventResultGenerator;
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
        SportEventDaoImpl sportEventDao = new SportEventDaoImpl(false);
//        SportEventValidatorImpl eventValidator = SportEventValidatorImpl
        SportEvent sportEvent = new SportEvent.SportEventBuilder()//after validation todo
                .setEventType(SportEventType.valueOf(eventData.get(EVENT_TYPE)))
                .setFirstTeam(eventData.get(FIRST_TEAM))
                .setFirstTeamRatio(BigDecimal.valueOf(Long.parseLong(eventData.get(FIRST_TEAM_RATIO))))
                .setSecondTeam(eventData.get(SECOND_TEAM))
                .setSecondTeamRatio(BigDecimal.valueOf(Long.parseLong(eventData.get(SECOND_TEAM_RATIO))))
                .setEventDate(LocalDate.parse(eventData.get(EVENT_DATE)))
                .build();
        try {
            if (!sportEventDao.insert(sportEvent)) {
                return true;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while adding event: " + e);
            throw new ServiceException(e);
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
        List<Bet> betsForFirstTeam;
        try {
            transaction.begin(eventDao, betDao);
            betsForFirstTeam = betDao.findProcessingBetsForProvidedTeamByEventId(sportEvent.getUniqueEventId(), firstTeam);
            List<Bet> betsForSecondTeam = betDao.findProcessingBetsForProvidedTeamByEventId(sportEvent.getUniqueEventId(), secondTeam);
            switch (firstTeamResult.compareTo(secondTeamResult)) {
                case LOST:
                    for (Bet bet : betsForFirstTeam) {
                        betService.calculateBetSummary(bet, LOST);
                    }
                    for (Bet bet : betsForSecondTeam) {
                        betService.calculateBetSummary(bet, WIN);
                    }
                    break;
                case DRAWN:
                    for (Bet bet : betsForFirstTeam) {
                        betService.calculateBetSummary(bet, DRAWN);
                    }
                    for (Bet bet : betsForSecondTeam) {
                        betService.calculateBetSummary(bet, DRAWN);
                    }
                    break;
                case WIN:
                    for (Bet bet : betsForFirstTeam) {
                        betService.calculateBetSummary(bet, WIN);
                    }
                    for (Bet bet : betsForSecondTeam) {
                        betService.calculateBetSummary(bet, LOST);
                    }
                    break;
            }
            betsForFirstTeam.addAll(betsForSecondTeam);
            betDao.updateBetStatus(betsForFirstTeam);
            eventDao.delete(sportEvent);
            eventDao.insertPastSportEvent(sportEvent, eventResult.values().toString());
            transaction.commit();
        } catch (DaoException | ServiceException e) {
            try {
                transaction.rollback();
            } catch (DaoException exception) {
                LOGGER.error("Exception while rollback transaction: " + exception);
            }
            LOGGER.error("Exception while releasing event: " + e);
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException daoException) {
                LOGGER.error("Error has occurred while ending transaction for registering user: " + daoException);
            }
        }
        return betsForFirstTeam;
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
            throw new ServiceException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<SportEvent> findAllOngoingEvents() throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        List<SportEvent> ongoingEvents;
        try {
            ongoingEvents = eventDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Exception while finding events: " + e);
            throw new ServiceException(e);
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
            throw new ServiceException(e);
        }
        return pastEvents;
    }

    @Override
    public boolean deleteSportEvent(SportEvent sportEvent) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(true);
        BetDaoImpl betDao = new BetDaoImpl(true);
        UserDaoImpl userDao = new UserDaoImpl(true);
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Transaction transaction = Transaction.getInstance();
        try {
            transaction.begin(betDao, eventDao, userDao);
            List<Bet> bets = betDao.findAllBetsByEventId(sportEvent.getUniqueEventId());
            List<String> logins = new ArrayList<>();
            Map<String, BigDecimal> loginsAndBetAmount = new HashMap<>();
            for (Bet bet : bets) {
                String userLogin = bet.getUserLogin();
                BigDecimal betAmount = bet.getBetAmount();
                loginsAndBetAmount.put(userLogin, betAmount);
                bet.setBetStatus(BetStatus.DELETED);
            }
            List<User> users = userService.findUsersByLogins(logins);
            for (User user : users) {
                BigDecimal betAmount = loginsAndBetAmount.get(user.getLogin());
                BigDecimal updatedBalance = user.getBalance().add(betAmount);
                user.setBalance(updatedBalance);
            }
            if (userDao.updateUsersBalance(users)) {
                if (betDao.updateBetStatus(bets)) {
                    if (eventDao.delete(sportEvent)) {
                        transaction.commit();
                        return true;
                    }
                }
                transaction.rollback();
            }
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                LOGGER.error("Cant rollback transaction: " + ex);
            }
            LOGGER.error("Exception while deleting event: " + e);
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                LOGGER.error("Cant end transaction: " + e);
            }
        }
        return false;
    }

    @Override
    public void updateFirstTeamName(String eventId, String changeTo) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        boolean status = false;
        try {
            if (eventDao.updateFirstTeamName(eventId, changeTo)) {
                status = true;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating team name: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateSecondTeamName(String eventId, String changeTo) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        boolean status = false;//status todo
        try {
            if (eventDao.updateSecondTeamName(eventId, changeTo)) {
                status = true;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating team name: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateFirstTeamRatio(String eventId, BigDecimal changeTo) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        boolean status = false;
        try {
            if (eventDao.updateFirstTeamRatio(eventId, changeTo)) {
                status = true;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating team ratio: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateSecondTeamRatio(String eventId, BigDecimal changeTo) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        boolean status = false;
        try {
            if (eventDao.updateSecondTeamRatio(eventId, changeTo)) {
                status = true;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating team ratio: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateEventDate(String eventId, LocalDate date) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        boolean status = false;
        try {
            if (eventDao.updateEventDate(eventId, date)) {
                status = true;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating event date: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateEventType(String eventId, SportEventType eventType) throws ServiceException {
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        boolean status = false;
        try {
            if (eventDao.updateEventType(eventId, eventType)) {
                status = true;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating event type: " + e);
            throw new ServiceException(e);
        }
    }
}
