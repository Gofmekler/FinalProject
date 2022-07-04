package by.maiseichyk.finalproject.service;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Maiseichyk
 * The interface Bet service.
 * @project Totalizator
 */
public interface BetService {
    /**
     * @param betData the bet data
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean checkBalance(Map<String, String> betData) throws ServiceException;

    /**
     * @param betData the bet data
     * @param user    the user
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean insertBet(Map<String, String> betData, User user) throws ServiceException;

    /**
     * @param login the login
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Bet> findAllUserBetsByLogin(String login) throws ServiceException;

    /**
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Bet> findAllBets() throws ServiceException;

    /**
     * @param eventId the event id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Bet> findAllBetsByEventId(String eventId) throws ServiceException;

    /**
     * @param login     the login
     * @param betStatus the bet status
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Bet> findAllUserBetsByBetStatus(String login, String betStatus) throws ServiceException;

    /**
     * @param betId       the bet id
     * @param coefficient the win coefficient
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateWinCoefficient(String betId, String coefficient) throws ServiceException;

    /**
     * @param betId     the bet id
     * @param betStatus the bet status
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateBetStatus(String betId, String betStatus) throws ServiceException;

    /**
     * @param betId     the bet id
     * @param betAmount the bet amount
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateBetAmount(String betId, String betAmount) throws ServiceException;

    /**
     * @param betId the bet id
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean deleteBet(String betId) throws ServiceException;
}
