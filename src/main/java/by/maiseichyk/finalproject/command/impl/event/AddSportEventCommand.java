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

import java.util.HashMap;
import java.util.Map;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;

public class AddSportEventCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EVENT_ADD_SUCCESS = "confirm.event.add";
    private static final String EVENT_ADD_ERROR = "error.event.add";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        SportEventServiceImpl eventService = SportEventServiceImpl.getInstance();
        Map<String, String> eventData = new HashMap<>();
        eventData.put(RequestParameter.EVENT_TYPE, request.getParameter(RequestParameter.EVENT_TYPE).toUpperCase());
        eventData.put(RequestParameter.FIRST_TEAM_NAME, request.getParameter(RequestParameter.FIRST_TEAM_NAME));
        eventData.put(RequestParameter.FIRST_TEAM_RATIO, request.getParameter(RequestParameter.FIRST_TEAM_RATIO));
        eventData.put(RequestParameter.SECOND_TEAM_NAME, request.getParameter(RequestParameter.SECOND_TEAM_NAME));
        eventData.put(RequestParameter.SECOND_TEAM_RATIO, request.getParameter(RequestParameter.SECOND_TEAM_RATIO));
        eventData.put(RequestParameter.EVENT_DATE, request.getParameter(RequestParameter.EVENT_DATE));
        try {
            if (eventService.addEvent(eventData)) {
                session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_ADD_SUCCESS);
                session.setAttribute(SessionAttribute.EVENTS, eventService.findAllOngoingEvents());
            } else {
                session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_ADD_ERROR);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while adding event: " + e);
            throw new CommandException("Exception while adding event: ", e);
        }
        return new Router(EVENTS, REDIRECT);
    }
}
