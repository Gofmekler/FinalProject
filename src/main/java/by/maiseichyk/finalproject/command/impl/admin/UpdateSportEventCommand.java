package by.maiseichyk.finalproject.command.impl.admin;

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
import static by.maiseichyk.finalproject.controller.Router.Type.*;
import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class UpdateSportEventCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        SportEventDaoImpl eventDao = SportEventDaoImpl.getInstance();
        SportEvent sportEvent = new SportEvent.SportEventBuilder()
                .setUniqueEventId(request.getParameter(UNIQUE_EVENT_ID))
                .setEventType(SportEventType.valueOf(request.getParameter(EVENT_TYPE)))
                .setEventDate(request.getParameter(EVENT_DATE))
                .setFirstTeam(request.getParameter(FIRST_TEAM))
                .setFirstTeamRatio(request.getParameter(FIRST_TEAM_RATIO))
                .setSecondTeam(request.getParameter(SECOND_TEAM))
                .setSecondTeamRatio(request.getParameter(SECOND_TEAM_RATIO))
                .build();
        try {
            if (eventDao.update(sportEvent)) {
                request.setAttribute("command_sport_event_msg", "Info updated successfully");
                session.setAttribute("events", eventDao.findAll());
            } else {
                request.setAttribute("command_sport_event_msg", "Cannot update event info");
            }
        } catch (DaoException e) {
            session.setAttribute("error_msg", "Exception in Event DAO. " + e);
            return new Router(ERROR_500, REDIRECT);
        }
        return new Router(EVENTS, FORWARD);
    }
}
