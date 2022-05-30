package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> authenticate(String login, String password) throws DaoException;
    BigDecimal getUserBalance(String login) throws DaoException;
    boolean updateUserBalance(BigDecimal balance, String login) throws DaoException;
    boolean updateUsersBalance(List<User> users) throws DaoException;
//    User findUserByLogin(String login) throws DaoException;//todo realisation
    boolean updateUsersBalanceByLogins(List<User> users) throws DaoException;
    Optional<User> findUserByLogin(String login) throws DaoException;
//    Optional<User> findUserByFirstName(String firstName);
//    Optional<User> findUserBySecondName(String secondName);
//    Optional<User> findUserByEmail(String email);
//    Optional<String> findUsersPassword(String login);

}
