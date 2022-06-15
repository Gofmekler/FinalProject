package by.maiseichyk.finalproject.util.validator;

import java.util.Map;

public interface SportEventValidator {
    boolean checkTeamName(String name);

    boolean checkTeamRatio(String ratio);

    boolean checkEventDate(String date);

    boolean checkEventType(String eventType);

    boolean checkEventData(Map<String, String> eventData);
}
