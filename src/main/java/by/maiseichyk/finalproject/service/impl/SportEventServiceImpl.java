package by.maiseichyk.finalproject.service.impl;

import by.maiseichyk.finalproject.dao.impl.BetDaoImpl;
import by.maiseichyk.finalproject.dao.impl.SportEventDaoImpl;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.service.SportEventService;
import by.maiseichyk.finalproject.util.EventResultGenerator;

import java.util.List;
import java.util.Map;

public class SportEventServiceImpl implements SportEventService {
    private static final SportEventServiceImpl instance = new SportEventServiceImpl();

    private SportEventServiceImpl() {
    }

    public static SportEventServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean isEventIdOccupied(String eventId) {
        return false;
    }

    @Override
    public boolean InsertEventResult(SportEvent sportEvent) {
        String firstTeam = sportEvent.getFirstTeam();
        String secondTeam = sportEvent.getSecondTeam();
        Map<String, Integer> eventResult = EventResultGenerator.generateResult(firstTeam, secondTeam);
        Integer firstTeamResult = eventResult.get(firstTeam);
        Integer secondTeamResult = eventResult.get(secondTeam);
        SportEventDaoImpl eventDao = SportEventDaoImpl.getInstance();
        BetDaoImpl betDao = BetDaoImpl.getInstance();
        BetServiceImpl betService = BetServiceImpl.getInstance();
        try {
            List<Bet> betsForFirstTeam = betDao.findBetsForProvidedTeamByEventID(sportEvent.getUniqueEventId(), firstTeam);//todo
            List<Bet> betsForSecondTeam = betDao.findBetsForProvidedTeamByEventID(sportEvent.getUniqueEventId(), secondTeam);
            switch (firstTeamResult.compareTo(secondTeamResult)) {
                case -1://less than constant todo
                    for (Bet bet : betsForFirstTeam) {
                        betService.revealBetSummary(bet, -1);//constant
                    }
                    for (Bet bet : betsForSecondTeam) {
                        betService.revealBetSummary(bet, 1);
                    }
                    break;
                case 0://equals todo
                    break;
                case 1://greater than
                    for (Bet bet : betsForFirstTeam) {
                        betService.revealBetSummary(bet, 1);//constant
                    }
                    for (Bet bet : betsForSecondTeam) {
                        betService.revealBetSummary(bet, -1);
                    }
                    break;
            }//TODO
        } catch (DaoException e) {
            e.printStackTrace();//todo log
        }
        return false;
    }
}
