package by.maiseichyk.finalproject.service.impl;

import by.maiseichyk.finalproject.dao.impl.BetDaoImpl;
import by.maiseichyk.finalproject.dao.impl.SportEventDaoImpl;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.SportEventService;
import by.maiseichyk.finalproject.util.EventResultGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SportEventServiceImpl implements SportEventService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SportEventServiceImpl instance = new SportEventServiceImpl();

    private SportEventServiceImpl() {
    }

    public static SportEventServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean isEventIdOccupied(String eventId) throws ServiceException {
        return false;
    }

    @Override
    public List<Bet> insertEventResult(SportEvent sportEvent) throws ServiceException {
        String firstTeam = sportEvent.getFirstTeam();
        String secondTeam = sportEvent.getSecondTeam();
        Map<String, Integer> eventResult = EventResultGenerator.generateResult(firstTeam, secondTeam);
        Integer firstTeamResult = eventResult.get(firstTeam);
        Integer secondTeamResult = eventResult.get(secondTeam);
        BetDaoImpl betDao = BetDaoImpl.getInstance();
        SportEventDaoImpl eventDao = SportEventDaoImpl.getInstance();
        BetServiceImpl betService = BetServiceImpl.getInstance();
        List<Bet> betsForFirstTeam;
        try {
            betsForFirstTeam = betDao.findProcessingBetsForProvidedTeamByEventID(sportEvent.getUniqueEventId(), firstTeam);//todo
            List<Bet> betsForSecondTeam = betDao.findProcessingBetsForProvidedTeamByEventID(sportEvent.getUniqueEventId(), secondTeam);
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
            betDao.updateBetStatus(betsForFirstTeam);//one list or divide todo
            eventDao.delete(sportEvent);
            eventDao.insertPastSportEvent(sportEvent);
        } catch (DaoException | ServiceException e) {
            LOGGER.error("Exception while identifying users: " + e);//todo log
            throw new ServiceException(e);
        }
        return betsForFirstTeam;
    }

    @Override
    public Optional<SportEvent> findSportEventById(String eventId) throws ServiceException {
        SportEventDaoImpl sportEventDao = SportEventDaoImpl.getInstance();
        try {
            Optional<SportEvent> sportEvent = sportEventDao.findSportEventInDb(eventId);
            if (sportEvent.isPresent()) {
                return sportEvent;
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<SportEvent> findAllOngoingEvents() throws ServiceException {
        SportEventDaoImpl sportEventDao = SportEventDaoImpl.getInstance();
        List<SportEvent> ongoingEvents;
        try{
            ongoingEvents = sportEventDao.findAll();
        } catch (DaoException e){
            LOGGER.error("Exception while finding events: " + e);
            throw new ServiceException(e);
        }
        return ongoingEvents;
    }

    @Override
    public List<SportEvent> findAllPastEvents() throws ServiceException {
        SportEventDaoImpl sportEventDao = SportEventDaoImpl.getInstance();
        List<SportEvent> pastEvents;
        try{
            pastEvents = sportEventDao.findAllPastEvents();
        } catch (DaoException e){
            LOGGER.error("Exception while finding past events: " + e);
            throw new ServiceException(e);
        }
        return pastEvents;
    }
}
