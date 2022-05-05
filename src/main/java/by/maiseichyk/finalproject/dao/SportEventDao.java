package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.exception.DaoException;

import java.util.Optional;

public interface SportEventDao {
    Optional<SportEvent> findSportEventInDb(SportEvent event) throws DaoException;
}
