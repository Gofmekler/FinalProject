package by.maiseichyk.finalproject.command.adminCommand;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.dao.impl.SportEventDaoImpl;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.SportEventType;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class AddSportEventCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        Router router = null;
        SportEventDaoImpl eventDao = SportEventDaoImpl.getInstance();
        SportEvent event = new SportEvent.SportEventBuilder()
                .setUniqueEventId(request.getParameter(UNIQUE_EVENT_ID))
                .setEventType(SportEventType.valueOf(request.getParameter(EVENT_TYPE)))
                .setFirstTeam(request.getParameter(FIRST_TEAM))
                .setFirstTeamRatio(request.getParameter(FIRST_TEAM_RATIO))
                .setSecondTeam(request.getParameter(SECOND_TEAM))
                .setSecondTeamRatio(request.getParameter(SECOND_TEAM_RATIO))
                .setEventDate(request.getParameter(EVENT_DATE))
                .build();
        try {
            if (eventDao.insert(event)) {
                session.setAttribute("command_sport_event_msg", "Inserted successfully");
            } else {
                session.setAttribute("command_sport_event_msg", "Cannot insert new event");
            }
        } catch (DaoException e) {
            session.setAttribute("error_msg", "Exception in DAO " + e);
            return new Router(ERROR_500, Router.Type.REDIRECT);
        }

        return new Router(EVENTS, Router.Type.FORWARD);
    }
}
