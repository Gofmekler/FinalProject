package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.BetStatus;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BetDao {
    List<Bet> findAllUserBetsByLogin(String userLogin) throws DaoException;
    List<Bet> findAllBetsByEventId(String eventId) throws DaoException;
    List<Bet> findProcessingBetsForProvidedTeamByEventID(String eventId, String providedTeam) throws DaoException;
    boolean updateBetStatus(List<Bet> bets) throws DaoException;
}
