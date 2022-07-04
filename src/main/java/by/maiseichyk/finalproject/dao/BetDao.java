package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.BetStatus;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @project Totalizator
 * @author Maiseichyk
 * The interface Bet dao.
 */
public interface BetDao {
    /**
     * @param userLogin the user login
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Bet> findAllUserBetsByLogin(String userLogin) throws DaoException;

    /**
     * @param eventId the event id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Bet> findAllBetsByEventId(int eventId) throws DaoException;

    /**
     * @param eventId the event id
     * @param providedTeam the provided team
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Bet> findProcessingBetsForProvidedTeamByEventId(int eventId, String providedTeam) throws DaoException;

    /**
     * @param bets the bet list
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateBetStatus(List<Bet> bets) throws DaoException;

    /**
     * @param login the login
     * @param status the bet status
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Bet> findAllUserBetsByBetStatus(String login, BetStatus status) throws DaoException;

    /**
     * @param betId the bet id
     * @param betAmount the bet amount
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateBetAmount(String betId, BigDecimal betAmount) throws DaoException;

    /**
     * @param betId the bet id
     * @param betStatus the bet status
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateBetStatus(String betId, BetStatus betStatus) throws DaoException;

    /**
     * @param betId the bet id
     * @param coefficient the win coefficient
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateWinCoefficient(String betId, BigDecimal coefficient) throws DaoException;

    /**
     * @param betId the bet id
     * @return the bet optional
     * @throws DaoException the dao exception
     */
    Optional<Bet> findBet(String betId) throws DaoException;
}
