package by.maiseichyk.finalproject.service.impl;

import by.maiseichyk.finalproject.dao.Transaction;
import by.maiseichyk.finalproject.dao.impl.BetDaoImpl;
import by.maiseichyk.finalproject.dao.impl.SportEventDaoImpl;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.SportEventType;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.SportEventService;
import by.maiseichyk.finalproject.util.EventResultGenerator;
import by.maiseichyk.finalproject.util.validator.impl.SportEventValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class SportEventServiceImpl implements SportEventService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SportEventServiceImpl instance = new SportEventServiceImpl();

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
            betsForFirstTeam = betDao.findProcessingBetsForProvidedTeamByEventId(sportEvent.getUniqueEventId(), firstTeam);//todo
            List<Bet> betsForSecondTeam = betDao.findProcessingBetsForProvidedTeamByEventId(sportEvent.getUniqueEventId(), secondTeam);
            switch (firstTeamResult.compareTo(secondTeamResult)) {
                case -1://less than constant todo
                    for (Bet bet : betsForFirstTeam) {
                        betService.calculateBetSummary(bet, -1);//constant
                    }
                    for (Bet bet : betsForSecondTeam) {
                        betService.calculateBetSummary(bet, 1);
                    }
                    break;
                case 0://equals todo
                    for (Bet bet : betsForFirstTeam) {
                        betService.calculateBetSummary(bet, 0);//constant
                    }
                    for (Bet bet : betsForSecondTeam) {
                        betService.calculateBetSummary(bet, 0);
                    }

                    break;
                case 1://greater than
                    for (Bet bet : betsForFirstTeam) {
                        betService.calculateBetSummary(bet, 1);//constant
                    }
                    for (Bet bet : betsForSecondTeam) {
                        betService.calculateBetSummary(bet, -1);
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
        SportEventDaoImpl eventDao = new SportEventDaoImpl(false);
        try {
            if (eventDao.delete(sportEvent)) {
                return true;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while deleting event: " + e);
            throw new ServiceException(e);
        }
        return false;
    }
}
