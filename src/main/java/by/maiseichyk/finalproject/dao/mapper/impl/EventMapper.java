package by.maiseichyk.finalproject.dao.mapper.impl;

import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.SportEventType;
import by.maiseichyk.finalproject.dao.mapper.Mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class EventMapper implements Mapper<SportEvent> {
    private static final EventMapper instance = new EventMapper();

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
                    .setId(Integer.parseInt(resultSet.getString(UNIQUE_EVENT_ID)))
                    .setEventType(SportEventType.valueOf(resultSet.getString(EVENT_TYPE)))
                    .setFirstTeam(resultSet.getString(FIRST_TEAM))
                    .setFirstTeamRatio(resultSet.getBigDecimal(FIRST_TEAM_RATIO))
                    .setSecondTeam(resultSet.getString(SECOND_TEAM))
                    .setSecondTeamRatio(resultSet.getBigDecimal(SECOND_TEAM_RATIO))
                    .setEventDate(LocalDate.parse(resultSet.getString(EVENT_DATE)))
                    .setEventResult(resultSet.getString(EVENT_RESULT))
                    .build();
            sportEventList.add(event);
        }
        return sportEventList;
    }
}
