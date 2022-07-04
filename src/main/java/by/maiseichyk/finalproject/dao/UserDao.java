package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.entity.UserRole;
import by.maiseichyk.finalproject.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author Maiseichyk
 * The interface User dao.
 * @project Totalizator
 */
public interface UserDao {
    /**
     * @param login   the login
     * @param balance the balance
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserBalance(String login, BigDecimal balance) throws DaoException;

    /**
     * @param users the user list
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateUsersBalance(List<User> users) throws DaoException;

    /**
     * @param login the login
     * @return optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByLogin(String login) throws DaoException;

    /**
     * @param email the email
     * @return optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByEmail(String email) throws DaoException;

    /**
     * @param name     the name
     * @param lastname the lastname
     * @param email    the email
     * @return optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByFullNameAndEmail(String name, String lastname, String email) throws DaoException;

    /**
     * @param name     the name
     * @param lastname the lastname
     * @param role     the role
     * @return optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByFullNameAndRole(String name, String lastname, UserRole role) throws DaoException;

    /**
     * @param role the role
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findUsersByRole(UserRole role) throws DaoException;

    /**
     * @param user the user
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserRole(User user) throws DaoException;

    /**
     * @param login    the login
     * @param changeTo the login to change
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserLogin(String login, String changeTo) throws DaoException;

    /**
     * @param login    the login
     * @param password the password
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserPass(String login, String password) throws DaoException;

    /**
     * @param login the login
     * @param name  the name
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserName(String login, String name) throws DaoException;

    /**
     * @param login    the login
     * @param lastName the lastname
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserLastName(String login, String lastName) throws DaoException;

    /**
     * @param login the login
     * @param email the email
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserEmail(String login, String email) throws DaoException;

}
