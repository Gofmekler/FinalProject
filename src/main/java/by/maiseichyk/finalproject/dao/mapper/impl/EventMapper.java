package by.maiseichyk.finalproject.dao.mapper.impl;

import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.SportEventType;
import by.maiseichyk.finalproject.dao.mapper.Mapper;

import java.math.BigDecimal;
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
                    .setFirstTeamRatio(BigDecimal.valueOf(Long.parseLong(resultSet.getString(FIRST_TEAM_RATIO))))
                    .setSecondTeam(resultSet.getString(SECOND_TEAM))
                    .setSecondTeamRatio(BigDecimal.valueOf(Long.parseLong(resultSet.getString(SECOND_TEAM_RATIO))))
                    .setEventDate(resultSet.getString(EVENT_DATE))
                    .build();
            sportEventList.add(event);
        }
        return sportEventList;
    }
}
