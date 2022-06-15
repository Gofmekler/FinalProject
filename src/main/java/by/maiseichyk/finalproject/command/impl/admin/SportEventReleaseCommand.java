package by.maiseichyk.finalproject.command.impl.admin;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.dao.impl.SportEventDaoImpl;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.SportEventServiceImpl;
import by.maiseichyk.finalproject.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.maiseichyk.finalproject.command.PagePath.EVENTS;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;

public class SportEventReleaseCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String eventId = request.getParameter("event_id");
        UserServiceImpl userService = UserServiceImpl.getInstance();
        SportEventServiceImpl sportEventService = SportEventServiceImpl.getInstance();
//        SportEventDaoImpl eventDao = SportEventDaoImpl.getInstance();
        List<String> logins = new ArrayList<>();
        List<User> usersWhoTakePartInEvent;
        try {
            Optional<SportEvent> sportEvent = sportEventService.findSportEventById(eventId);
            if (sportEvent.isPresent()) {
                List<Bet> bets = sportEventService.insertEventResult(sportEvent.get());
                for (Bet bet : bets) {
                    logins.add(bet.getUserLogin());
                }
                usersWhoTakePartInEvent = userService.findUsersByLogins(logins);
                if (userService.identificateWinnersDrawnersLosers(bets, usersWhoTakePartInEvent)){
                    session.setAttribute("event_msg", "Released successfully");
                } else {
                    session.setAttribute("event_msg", "Problem while releasing event");
                }
            }
            List<SportEvent> events = sportEventService.findAllOngoingEvents();
            session.setAttribute("events", events);
        } catch (ServiceException e) {
            e.printStackTrace();//todo log
        }
        return new Router(EVENTS, FORWARD);
    }
}
