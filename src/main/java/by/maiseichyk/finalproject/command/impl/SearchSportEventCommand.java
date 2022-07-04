package by.maiseichyk.finalproject.command.impl;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestAttribute;
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

import java.util.List;

import static by.maiseichyk.finalproject.command.PagePath.SEARCH_EVENT;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;

public class SearchSportEventCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String NO_EVENTS_MESSAGE = "events.message";
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        SportEventServiceImpl eventService = SportEventServiceImpl.getInstance();
        HttpSession session  = request.getSession();
        String firstTeamName = request.getParameter(RequestParameter.FIRST_TEAM_NAME);
        String secondTeamName = request.getParameter(RequestParameter.SECOND_TEAM_NAME);
        try {
            List<SportEvent> sportEventList = eventService.findSportEventByTeamsName(firstTeamName, secondTeamName);
            if (sportEventList.isEmpty()){
                request.setAttribute(RequestAttribute.EVENT_SEARCH_MESSAGE, NO_EVENTS_MESSAGE);
            } else {
                session.setAttribute(SessionAttribute.EVENTS_BY_NAMES, sportEventList);
            }
        } catch (ServiceException e){
            LOGGER.error("Exception while finding events by team names. " + e);
            throw new CommandException("Exception while finding events by team names. ", e);
        }
        return new Router(SEARCH_EVENT, FORWARD);
    }
}
