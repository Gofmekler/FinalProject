package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SportEventDao {
    Optional<SportEvent> findSportEventInDb(String eventId) throws DaoException;
    boolean insertSportEventResultInDb(String result) throws DaoException;
    boolean insertPastSportEvent(SportEvent sportEvent) throws DaoException;
    List<SportEvent> findAllPastEvents() throws DaoException;
}
