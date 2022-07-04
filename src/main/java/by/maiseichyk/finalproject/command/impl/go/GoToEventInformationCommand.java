package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestParameter;
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

import java.util.Optional;

import static by.maiseichyk.finalproject.command.PagePath.EVENTS;
import static by.maiseichyk.finalproject.command.PagePath.EVENT_INFO;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;
import static by.maiseichyk.finalproject.controller.Router.Type.REDIRECT;

public class GoToEventInformationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        SportEventServiceImpl eventService = SportEventServiceImpl.getInstance();
        String eventId = request.getParameter(RequestParameter.EVENT_ID);
        try {
            Optional<SportEvent> event = eventService.findSportEventById(eventId);
            if (event.isPresent()) {
                request.setAttribute(SessionAttribute.EVENT, event.get());
                return new Router(EVENT_INFO, FORWARD);
            } else {
                return new Router(EVENTS, FORWARD);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while searching event. " + e);
            throw new CommandException("Exception while searching event. ", e);
        }
    }
}
