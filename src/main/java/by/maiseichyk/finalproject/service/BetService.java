package by.maiseichyk.finalproject.service;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface BetService {
    boolean checkBalance(String userLogin, BigDecimal betAmount) throws ServiceException;
    boolean insertBet(Bet bet, User user) throws ServiceException;
    Bet revealBetSummary(Bet bet, int eventResult);
//    boolean setBetSummary(Bet bet);
}
