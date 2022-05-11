package by.maiseichyk.finalproject.mapper.impl;

import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.SportEventType;
import by.maiseichyk.finalproject.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class EventMapper implements Mapper<SportEvent> {
    private static EventMapper instance = new EventMapper();

    private EventMapper() {
    }

    public static EventMapper getInstance() {
        return instance;
    }

    @Override
    public List<SportEvent> retrieve(ResultSet resultSet) throws SQLException {
        List<SportEvent> sportEventList = new ArrayList<>();
        while (resultSet.next()) {
            SportEvent event = new SportEvent.SportEventBuilder()
                    .setUniqueEventId(resultSet.getString(UNIQUE_EVENT_ID))
                    .setEventType(SportEventType.valueOf(resultSet.getString(EVENT_TYPE)))
                    .setFirstTeam(resultSet.getString(FIRST_TEAM))
                    .setFirstTeamRatio(resultSet.getString(FIRST_TEAM_RATIO))
                    .setSecondTeam(resultSet.getString(SECOND_TEAM))
                    .setSecondTeamRatio(resultSet.getString(SECOND_TEAM_RATIO))
                    .setEventDate(resultSet.getString(EVENT_DATE))
                    .build();
            sportEventList.add(event);
        }
        return sportEventList;
    }
}
