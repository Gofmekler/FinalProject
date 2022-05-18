package by.maiseichyk.finalproject.service;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.User;

public interface BetService {
    boolean checkBalance(User user, Bet bet);

}
