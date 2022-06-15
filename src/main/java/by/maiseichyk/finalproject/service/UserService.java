package by.maiseichyk.finalproject.service;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.entity.UserType;
import by.maiseichyk.finalproject.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    boolean isLoginOccupied(String login) throws ServiceException;

    boolean isEmailOccupied(String email) throws ServiceException;

    Optional<User> findUser(String login, String password) throws ServiceException;

    boolean checkUserAge(String birthDate) throws ServiceException;

    boolean identificateWinnersDrawnersLosers(List<Bet> bets, List<User> users) throws ServiceException;

    List<User> findUsersByLogins(List<String> logins) throws ServiceException;

    boolean registerUser(Map<String, String> userData) throws ServiceException;

    List<User> findAllUsers() throws ServiceException;

    boolean checkUserBalance(User user, BigDecimal amount);

    boolean deleteUser(User user) throws ServiceException;

    void updateUserRole(UserType userType, String login) throws ServiceException;

    void updateUserLogin(String login, String changeTo) throws ServiceException;

    void updateUserPass(String login, String password) throws ServiceException;

    void updateUserName(String login, String name) throws ServiceException;

    void updateUserLastName(String login, String lastName) throws ServiceException;

    void updateUserEmail(String login, String email) throws ServiceException;

    boolean updateUserBalance(String login, BigDecimal balance) throws ServiceException;
}
