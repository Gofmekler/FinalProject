package by.maiseichyk.finalproject.command.impl.event;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.SportEventServiceImpl;
import by.maiseichyk.finalproject.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.maiseichyk.finalproject.command.PagePath.EVENTS;
import static by.maiseichyk.finalproject.command.RequestParameter.EVENT_ID;
import static by.maiseichyk.finalproject.controller.Router.Type.REDIRECT;

public class SportEventReleaseCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EVENT_RELEASE_SUCCESS = "confirm.event.release";
    private static final String EVENT_RELEASE_ERROR = "error.event.release";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String eventId = request.getParameter(EVENT_ID);
        UserServiceImpl userService = UserServiceImpl.getInstance();
        SportEventServiceImpl sportEventService = SportEventServiceImpl.getInstance();
        List<String> logins = new ArrayList<>();
        List<User> usersWhoTakePartInEvent;
        try {
            Optional<SportEvent> sportEvent = sportEventService.findSportEventById(eventId);
            if (sportEvent.isPresent()) {
                List<Bet> bets = sportEventService.insertEventResult(sportEvent.get());
                for (Bet bet : bets) {
                    logins.add(bet.getUserLogin());
                }
                usersWhoTakePartInEvent = userService.findUsers(logins);
                if (userService.identificateWinnersDrawnersLosers(bets, usersWhoTakePartInEvent)) {
                    session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_RELEASE_SUCCESS);
                } else {
                    session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_RELEASE_ERROR);
                }
            }
            List<SportEvent> events = sportEventService.findAllOngoingEvents();
            session.setAttribute(SessionAttribute.EVENTS, events);
        } catch (ServiceException e) {
            LOGGER.error("Exception while releasing event. " + e);
            throw new CommandException("Exception while releasing event. ", e);
        }
        return new Router(EVENTS, REDIRECT);
    }
}
