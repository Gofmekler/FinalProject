package by.maiseichyk.finalproject.service;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findUser(String login, String password) throws ServiceException;
//    User updateUserBalanceAfterEventResult(User user, BigDecimal teamRatio, BigDecimal betAmount, boolean isWinner) throws ServiceException;
    boolean identificateWinnersDrawnersLosers(List<Bet> bets, List<User> users) throws ServiceException;//rename todo
    List<User> findUsersByLogins(List<String> logins) throws ServiceException;
}
