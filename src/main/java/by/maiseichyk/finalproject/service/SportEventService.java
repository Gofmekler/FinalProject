package by.maiseichyk.finalproject.service;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface SportEventService {
    boolean isEventIdOccupied(String eventId) throws ServiceException;
    List<Bet> insertEventResult(SportEvent sportEvent) throws ServiceException;
    Optional<SportEvent> findSportEventById(String eventId) throws ServiceException;
    List<SportEvent> findAllOngoingEvents() throws ServiceException;
    List<SportEvent> findAllPastEvents() throws ServiceException;

}
