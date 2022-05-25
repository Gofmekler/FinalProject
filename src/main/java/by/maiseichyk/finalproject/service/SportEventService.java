package by.maiseichyk.finalproject.service;

import by.maiseichyk.finalproject.entity.SportEvent;

public interface SportEventService {
    boolean isEventIdOccupied(String eventId);//todo service exc
    boolean InsertEventResult(SportEvent sportEvent);

}
