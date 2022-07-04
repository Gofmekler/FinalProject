package by.maiseichyk.finalproject.command.impl.event;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.SportEventServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;

public class DeleteSportEventCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EVENT_DELETE_SUCCESS = "confirm.event.delete";
    private static final String EVENT_DELETE_ERROR = "error.event.delete";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        SportEventServiceImpl eventService = SportEventServiceImpl.getInstance();
        String eventId = request.getParameter(RequestParameter.EVENT_ID);
        try {
            if (eventService.deleteSportEvent(eventId)) {
                request.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_DELETE_SUCCESS);
                session.setAttribute(SessionAttribute.EVENTS, eventService.findAllOngoingEvents());
            } else {
                request.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_DELETE_ERROR);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while deleting event. " + e);
            throw new CommandException("Exception while deleting event. ", e);
        }
        return new Router(EVENTS, REDIRECT);
    }
}
