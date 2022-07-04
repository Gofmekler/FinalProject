package by.maiseichyk.finalproject.util.validator.impl;

import by.maiseichyk.finalproject.entity.SportEventType;
import by.maiseichyk.finalproject.util.validator.SportEventValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static by.maiseichyk.finalproject.command.RequestParameter.FIRST_TEAM_NAME;
import static by.maiseichyk.finalproject.command.RequestParameter.SECOND_TEAM_NAME;
import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class SportEventValidatorImpl implements SportEventValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SportEventValidatorImpl instance = new SportEventValidatorImpl();
    private static final String INCORRECT_VALUE_PARAMETER = "incorrect";
    private static final String NAME_REGEX = "[A-Z\\p{Upper}][a-z\\p{Lower}]{1,15}";
    private static final String RATIO_REGEX = "\\d+.*\\d*";
    private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    private SportEventValidatorImpl() {
    }

    public static SportEventValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean checkTeamName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    @Override
    public boolean checkTeamRatio(String ratio) {
        return ratio != null && ratio.matches(RATIO_REGEX);
    }

    @Override
    public boolean checkEventDate(String date) {
        return date != null && date.matches(DATE_REGEX);
    }

    @Override
    public boolean checkEventType(String eventType) {
        if (eventType != null && !eventType.isBlank()) {
            List<SportEventType> eventTypeList = Arrays.stream(SportEventType.values()).toList();
            for (SportEventType sportEventType : eventTypeList) {
                if (sportEventType.toString().equals(eventType)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkEventData(Map<String, String> eventData) {
        boolean match = true;
        if (!checkTeamName(eventData.get(FIRST_TEAM_NAME))) {
            eventData.put(FIRST_TEAM, eventData.get(FIRST_TEAM_NAME) + INCORRECT_VALUE_PARAMETER);
            match = false;
        }
        if (!checkTeamName(eventData.get(SECOND_TEAM_NAME))) {
            eventData.put(SECOND_TEAM, eventData.get(SECOND_TEAM_NAME) + INCORRECT_VALUE_PARAMETER);
            match = false;
        }
        if (!checkTeamRatio(eventData.get(FIRST_TEAM_RATIO))) {
            eventData.put(FIRST_TEAM_RATIO, eventData.get(FIRST_TEAM_RATIO) + INCORRECT_VALUE_PARAMETER);
            match = false;
        }
        if (!checkTeamRatio(eventData.get(SECOND_TEAM_RATIO))) {
            eventData.put(SECOND_TEAM_RATIO, eventData.get(SECOND_TEAM_RATIO) + INCORRECT_VALUE_PARAMETER);
            match = false;
        }
        if (!checkEventDate(eventData.get(EVENT_DATE))) {
            eventData.put(EVENT_DATE, eventData.get(EVENT_DATE) + INCORRECT_VALUE_PARAMETER);
            match = false;
        }
        if (!checkEventType(eventData.get(EVENT_TYPE))) {
            eventData.put(EVENT_TYPE, eventData.get(EVENT_TYPE) + INCORRECT_VALUE_PARAMETER);
            match = false;
        }
        LOGGER.info(eventData.values());
        LOGGER.info(eventData.keySet());
        return match;
    }
}
