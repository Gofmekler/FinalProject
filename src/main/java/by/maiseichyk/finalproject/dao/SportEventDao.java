package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface SportEventDao {
    Optional<SportEvent> findSportEventInDb(String eventId) throws DaoException;

    boolean insertPastSportEvent(SportEvent sportEvent, String result) throws DaoException;

    List<SportEvent> findAllPastEvents() throws DaoException;
}
