package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    BigDecimal getUserBalance(String login) throws DaoException;

    boolean updateUserBalance(BigDecimal balance, String login) throws DaoException;

    boolean updateUsersBalance(List<User> users) throws DaoException;

    boolean updateUsersBalanceByLogins(List<User> users) throws DaoException;

    Optional<User> findUserByLogin(String login) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

}
