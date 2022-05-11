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

import static by.maiseichyk.finalproject.command.PagePath.ERROR_500;
import static by.maiseichyk.finalproject.command.PagePath.EVENTS;
import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class DeleteSportEventCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        SportEventDaoImpl eventDao = SportEventDaoImpl.getInstance();
        SportEvent event = new SportEvent.SportEventBuilder()
                .setUniqueEventId(request.getParameter(UNIQUE_EVENT_ID))
                .build();
        try {
            if (eventDao.delete(event)) {
                session.setAttribute("command_sport_event_msg", "Delete successfully");
            } else {
                session.setAttribute("command_sport_event_msg", "Cannot delete event");
            }
        } catch (DaoException e) {
            session.setAttribute("error_msg", "Exception in DAO " + e);
            return new Router(ERROR_500, Router.Type.REDIRECT);
        }

        return new Router(EVENTS, Router.Type.FORWARD);
    }
}