package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.entity.UserType;
import by.maiseichyk.finalproject.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    boolean updateUserBalance(String login, BigDecimal balance) throws DaoException;

    boolean updateUsersBalance(List<User> users) throws DaoException;

    Optional<User> findUserByLogin(String login) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    boolean updateUserRole(User user) throws DaoException;

    boolean updateUserRole(UserType userType, String login) throws DaoException;

    boolean updateUserLogin(String login, String changeTo) throws DaoException;

    boolean updateUserPass(String login, String password) throws DaoException;

    boolean updateUserName(String login, String name) throws DaoException;

    boolean updateUserLastName(String login, String lastName) throws DaoException;

    boolean updateUserEmail(String login, String email) throws DaoException;

}
