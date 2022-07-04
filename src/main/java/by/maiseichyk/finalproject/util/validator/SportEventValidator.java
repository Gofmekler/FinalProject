package by.maiseichyk.finalproject.util.validator;

import java.util.Map;

/**
 * @author Maiseichyk
 * The interface Event validator.
 * @project Totalizator
 */
public interface SportEventValidator {
    /**
     * @param name the name
     * @return boolean
     */
    boolean checkTeamName(String name);

    /**
     * @param ratio the ratio
     * @return boolean
     */
    boolean checkTeamRatio(String ratio);

    /**
     * @param date the date
     * @return boolean
     */
    boolean checkEventDate(String date);

    /**
     * @param eventType the event type
     * @return boolean
     */
    boolean checkEventType(String eventType);

    /**
     * @param eventData the event data
     * @return boolean
     */
    boolean checkEventData(Map<String, String> eventData);
}
