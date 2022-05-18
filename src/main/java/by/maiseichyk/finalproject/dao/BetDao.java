package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.BetStatus;
import by.maiseichyk.finalproject.entity.User;

import java.util.List;
import java.util.Optional;

public interface BetDao {
    List<Optional<Bet>> findUserBetsByBetStatus(User user, BetStatus betStatus);
}
