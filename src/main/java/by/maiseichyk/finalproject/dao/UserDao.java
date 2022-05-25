package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserDao {
    Optional<User> authenticate(String login, String password) throws DaoException;
    BigDecimal getUserBalance(String login) throws DaoException;
    boolean updateUserBalance(BigDecimal balance, String login) throws DaoException;
//    Optional<User> findUserByLogin(String login);//todo realisation
//    Optional<User> findUserById(String id);
//    Optional<User> findUserByFirstName(String firstName);
//    Optional<User> findUserBySecondName(String secondName);
//    Optional<User> findUserByEmail(String email);
//    Optional<String> findUsersPassword(String login);

}
