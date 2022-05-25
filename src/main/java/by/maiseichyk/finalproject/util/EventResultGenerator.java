package by.maiseichyk.finalproject.util;

import java.util.HashMap;
import java.util.Map;

public class EventResultGenerator {
    private static final int MAX_RESULT_COUNT = 10;

    private EventResultGenerator() {
    }

    public static Map<String, Integer> generateResult(String firstTeamName, String secondTeamName) {
        Map<String, Integer> eventResult = new HashMap<>();
        int firstTeamResult = generateNumber();
        int secondTeamResult = generateNumber();
        eventResult.put(firstTeamName, firstTeamResult);
        eventResult.put(secondTeamName, secondTeamResult);
        return eventResult;
    }

    private static int generateNumber() {
        return (int) (Math.random() * MAX_RESULT_COUNT);
    }
}
