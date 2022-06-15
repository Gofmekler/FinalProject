package by.maiseichyk.finalproject.service;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.ServiceException;

import java.math.BigDecimal;

public interface BetService {
    boolean checkBalance(String userLogin, BigDecimal betAmount) throws ServiceException;

    boolean insertBet(Bet bet, User user) throws ServiceException;

    Bet calculateBetSummary(Bet bet, int eventResult) throws ServiceException;
}
