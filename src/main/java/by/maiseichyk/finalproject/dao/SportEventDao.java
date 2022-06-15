package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.SportEventType;
import by.maiseichyk.finalproject.exception.DaoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SportEventDao {
    Optional<SportEvent> findSportEventInDb(String eventId) throws DaoException;

    boolean insertPastSportEvent(SportEvent sportEvent, String result) throws DaoException;

    List<SportEvent> findAllPastEvents() throws DaoException;

    boolean updateFirstTeamName(String eventId, String changeTo) throws DaoException;

    boolean updateSecondTeamName(String eventId, String changeTo) throws DaoException;

    boolean updateFirstTeamRatio(String eventId, BigDecimal changeTo) throws DaoException;

    boolean updateSecondTeamRatio(String eventId, BigDecimal changeTo) throws DaoException;

    boolean updateEventDate(String eventId, LocalDate date) throws DaoException;

    boolean updateEventType(String eventId, SportEventType eventType) throws DaoException;
}
