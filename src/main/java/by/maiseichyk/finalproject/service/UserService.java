package by.maiseichyk.finalproject.service;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.entity.UserRole;
import by.maiseichyk.finalproject.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Maiseichyk
 * The interface User service.
 * @project Totalizator
 */
public interface UserService {
    /**
     * @param login the login
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean isLoginOccupied(String login) throws ServiceException;

    /**
     * @param email the email
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean isEmailOccupied(String email) throws ServiceException;

    /**
     * @param login    the login
     * @param password the password
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUser(String login, String password) throws ServiceException;

    /**
     * @param login the login
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUser(String login) throws ServiceException;

    /**
     * @param name     the name
     * @param lastname the lastname
     * @param role     the role
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUser(String name, String lastname, UserRole role) throws ServiceException;

    /**
     * @param name     the name
     * @param lastname the lastname
     * @param email    the email
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUser(String name, String lastname, String email) throws ServiceException;

    /**
     * @param logins the logins
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findUsers(List<String> logins) throws ServiceException;

    /**
     * @param role the role
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findUsers(String role) throws ServiceException;

    /**
     * @param birthDate the birthdate
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean checkUserLegalAge(String birthDate) throws ServiceException;

    /**
     * @param bets  the bets
     * @param users the users
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean identificateWinnersDrawnersLosers(List<Bet> bets, List<User> users) throws ServiceException;

    /**
     * @param userData the user data
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean registerUser(Map<String, String> userData) throws ServiceException;

    /**
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findAllUsers() throws ServiceException;

    /**
     * @param user   the user
     * @param amount the amount
     * @return boolean
     */
    boolean checkUserBalance(User user, BigDecimal amount);

    /**
     * @param login the login
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean deleteUser(String login) throws ServiceException;

    /**
     * @param userType the user role
     * @param login    the login
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserRole(UserRole userType, String login) throws ServiceException;

    /**
     * @param login    the login
     * @param changeTo the login to change
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserLogin(String login, String changeTo) throws ServiceException;

    /**
     * @param login    the login
     * @param password the password
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserPass(String login, String password) throws ServiceException;

    /**
     * @param login the login
     * @param name  the name
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserName(String login, String name) throws ServiceException;

    /**
     * @param login    the login
     * @param lastname the lastname
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserLastname(String login, String lastname) throws ServiceException;

    /**
     * @param login the login
     * @param email the email
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserEmail(String login, String email) throws ServiceException;

    /**
     * @param login   the login
     * @param balance the balance
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserBalance(String login, BigDecimal balance) throws ServiceException;
}
