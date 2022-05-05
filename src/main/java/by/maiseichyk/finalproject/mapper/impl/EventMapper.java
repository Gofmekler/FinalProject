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
    private EventMapper(){
    }

    public static EventMapper getInstance() {
        return instance;
    }

    @Override
    public List<SportEvent> retrieve(ResultSet resultSet) throws SQLException {
        List<SportEvent> sportEventList = new ArrayList<>();
        while(resultSet.next()){
        SportEvent event = new SportEvent();
        event.setUnique_event_id(resultSet.getString(UNIQUE_EVENT_ID));
        event.setEventType(SportEventType.valueOf(resultSet.getString(EVENT_TYPE)));
        event.setFirstTeam(resultSet.getString(FIRST_TEAM));
        event.setFirstTeamRatio(resultSet.getString(FIRST_TEAM_RATIO));
        event.setSecondTeam(resultSet.getString(SECOND_TEAM));
        event.setSecondTeamRatio(resultSet.getString(SECOND_TEAM_RATIO));
        event.setEvent_date(resultSet.getString(EVENT_DATE));
        sportEventList.add(event);
        }
        return sportEventList;
    }
}
