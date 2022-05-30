package by.maiseichyk.finalproject.command.impl.admin;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.dao.impl.SportEventDaoImpl;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.SportEventType;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.util.EventResultGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;
import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class AddSportEventCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        SportEventDaoImpl eventDao = SportEventDaoImpl.getInstance();
        SportEvent event = new SportEvent.SportEventBuilder()
                .setUniqueEventId(request.getParameter(UNIQUE_EVENT_ID))
                .setEventType(SportEventType.valueOf(request.getParameter(EVENT_TYPE).toUpperCase()))
                .setFirstTeam(request.getParameter(FIRST_TEAM))
                .setFirstTeamRatio(BigDecimal.valueOf(Long.parseLong(request.getParameter(FIRST_TEAM_RATIO))))
                .setSecondTeam(request.getParameter(SECOND_TEAM))
                .setSecondTeamRatio(BigDecimal.valueOf(Long.parseLong(request.getParameter(SECOND_TEAM_RATIO))))
                .setEventDate(request.getParameter(EVENT_DATE))
                .build();
        try {
            if (eventDao.insert(event)) {
                request.setAttribute("command_sport_event_msg", "Inserted successfully");
                session.setAttribute("events", eventDao.findAll());
            } else {
                request.setAttribute("command_sport_event_msg", "Cannot insert new event");
            }
        } catch (DaoException e) {
            request.setAttribute("error_msg", "Exception in DAO " + e);
            return new Router(ERROR_500, REDIRECT);
        }

        return new Router(EVENTS, FORWARD);
    }
}
