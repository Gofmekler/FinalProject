package by.maiseichyk.finalproject.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
/**
 * @author Maiseichyk
 * The interface Event Result Generator.
 * @project Totalizator
 */
public class EventResultGenerator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int MAX_RESULT_COUNT = 10;

    private EventResultGenerator() {
    }

    /**
     * @param firstTeamName the first team name
     * @param secondTeamName the second team name
     * @return the map
     */
    public static Map<String, Integer> generateResult(String firstTeamName, String secondTeamName) {
        Map<String, Integer> eventResult = new HashMap<>();
        int firstTeamResult = generateNumber();
        int secondTeamResult = generateNumber();
        eventResult.put(firstTeamName, firstTeamResult);
        eventResult.put(secondTeamName, secondTeamResult);
        return eventResult;
    }

    private static int generateNumber() {
        LOGGER.info("Event result generated.");
        return (int) (Math.random() * MAX_RESULT_COUNT);
    }
}
