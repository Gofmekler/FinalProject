package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.SportEventType;
import by.maiseichyk.finalproject.exception.DaoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
/**
 * @project Totalizator
 * @author Maiseichyk
 * The interface Event dao.
 */
public interface SportEventDao {
    /**
     * @param eventId the event id
     * @return the optional event
     * @throws DaoException the dao exception
     */
    Optional<SportEvent> findSportEventInDb(String eventId) throws DaoException;

    /**
     * @param firstTeamName the first team name
     * @param secondTeamName the second team name
     * @return the list
     * @throws DaoException the dao exception
     */
    List<SportEvent> findSportEventByTeamsName(String firstTeamName, String secondTeamName) throws DaoException;

    /**
     * @param sportEvent the sport event
     * @param result the event result
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean insertPastSportEvent(SportEvent sportEvent, String result) throws DaoException;

    /**
     * @return the list
     * @throws DaoException the dao exception
     */
    List<SportEvent> findAllPastEvents() throws DaoException;

    /**
     * @param eventId the event id
     * @param changeTo the team name to change
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateFirstTeamName(String eventId, String changeTo) throws DaoException;

    /**
     * @param eventId the event id
     * @param changeTo the team name to change
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateSecondTeamName(String eventId, String changeTo) throws DaoException;

    /**
     * @param eventId the event id
     * @param changeTo ratio to change
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateFirstTeamRatio(String eventId, BigDecimal changeTo) throws DaoException;

    /**
     * @param eventId the event id
     * @param changeTo ratio to change
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateSecondTeamRatio(String eventId, BigDecimal changeTo) throws DaoException;

    /**
     * @param eventId the event id
     * @param date the date to change
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateEventDate(String eventId, LocalDate date) throws DaoException;

    /**
     * @param eventId the event id
     * @param eventType the event type to change
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateEventType(String eventId, SportEventType eventType) throws DaoException;
}
