package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestAttribute;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.SportEventServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.maiseichyk.finalproject.command.PagePath.PAST_EVENTS;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;

public class GoToPastEventCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String NO_EVENT_MESSAGE = "events.message";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        SportEventServiceImpl eventService = SportEventServiceImpl.getInstance();
        HttpSession session = request.getSession();
        try {
            List<SportEvent> eventList = eventService.findAllPastEvents();
            if (eventList.isEmpty()) {
                request.setAttribute(RequestAttribute.EVENT_LIST_MESSAGE, NO_EVENT_MESSAGE);
            } else {
                session.setAttribute(SessionAttribute.PAST_EVENTS, eventList);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while finding past events. " + e);
            throw new CommandException("Exception while finding past events. ", e);
        }
        return new Router(PAST_EVENTS, FORWARD);
    }
}
