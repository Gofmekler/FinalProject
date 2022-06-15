package by.maiseichyk.finalproject.util.validator.impl;

import by.maiseichyk.finalproject.util.validator.SportEventValidator;

import java.util.Map;

public class SportEventValidatorImpl implements SportEventValidator {
    private static final SportEventValidatorImpl instance = new SportEventValidatorImpl();
    private static final String NAME_REGEX = "";
    private static final String RATIO_REGEX = "";
    private static final String DATE_REGEX = "";
    private static final String TYPE_REGEX = "";

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
        return eventType != null && eventType.matches(TYPE_REGEX);
    }

    @Override
    public boolean checkEventData(Map<String, String> eventData) {
        return false;
    }
}
