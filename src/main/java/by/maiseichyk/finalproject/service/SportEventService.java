package by.maiseichyk.finalproject.service;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.SportEventType;
import by.maiseichyk.finalproject.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Maiseichyk
 * The interface Event service.
 * @project Totalizator
 */
public interface SportEventService {
    /**
     * @param eventData the event data
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean addEvent(Map<String, String> eventData) throws ServiceException;

    /**
     * @param sportEvent the sport event
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Bet> insertEventResult(SportEvent sportEvent) throws ServiceException;

    /**
     * @param eventId the event id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<SportEvent> findSportEventById(String eventId) throws ServiceException;

    /**
     * @param firstTeamName  the first team name
     * @param secondTeamName the second team name
     * @return the list
     * @throws ServiceException the service exception
     */
    List<SportEvent> findSportEventByTeamsName(String firstTeamName, String secondTeamName) throws ServiceException;

    /**
     * @return the list
     * @throws ServiceException the service exception
     */
    List<SportEvent> findAllOngoingEvents() throws ServiceException;

    /**
     * @return the list
     * @throws ServiceException the service exception
     */
    List<SportEvent> findAllPastEvents() throws ServiceException;

    /**
     * @param eventId the event id
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean deleteSportEvent(String eventId) throws ServiceException;

    /**
     * @param eventId  the event id
     * @param changeTo the team name to change
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateFirstTeamName(String eventId, String changeTo) throws ServiceException;

    /**
     * @param eventId  the event id
     * @param changeTo the team name to change
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateSecondTeamName(String eventId, String changeTo) throws ServiceException;

    /**
     * @param eventId  the event id
     * @param changeTo the team ratio to change
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateFirstTeamRatio(String eventId, BigDecimal changeTo) throws ServiceException;

    /**
     * @param eventId  the event id
     * @param changeTo the team ratio to change
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateSecondTeamRatio(String eventId, BigDecimal changeTo) throws ServiceException;

    /**
     * @param eventId the event id
     * @param date    the date
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateEventDate(String eventId, LocalDate date) throws ServiceException;

    /**
     * @param eventId   the event id
     * @param eventType the event type
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateEventType(String eventId, SportEventType eventType) throws ServiceException;
}
