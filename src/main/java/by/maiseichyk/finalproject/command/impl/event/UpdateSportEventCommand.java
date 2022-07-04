package by.maiseichyk.finalproject.command.impl.event;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.SportEventType;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.SportEventServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;

public class UpdateSportEventCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EVENT_UPDATE_SUCCESS = "confirm.event.update";
    private static final String EVENT_UPDATE_ERROR = "error.event.update";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        SportEventServiceImpl eventService = SportEventServiceImpl.getInstance();
        Map<String, String> eventData = new HashMap<>();
        String eventId = (String) session.getAttribute(SessionAttribute.EVENT_ID);
        eventData.put(RequestParameter.EVENT_TYPE, request.getParameter(RequestParameter.EVENT_TYPE).toUpperCase());
        eventData.put(RequestParameter.FIRST_TEAM_NAME, request.getParameter(RequestParameter.FIRST_TEAM_NAME));
        eventData.put(RequestParameter.FIRST_TEAM_RATIO, request.getParameter(RequestParameter.FIRST_TEAM_RATIO));
        eventData.put(RequestParameter.SECOND_TEAM_NAME, request.getParameter(RequestParameter.SECOND_TEAM_NAME));
        eventData.put(RequestParameter.SECOND_TEAM_RATIO, request.getParameter(RequestParameter.SECOND_TEAM_RATIO));
        eventData.put(RequestParameter.EVENT_DATE, request.getParameter(RequestParameter.EVENT_DATE));
        try {
            List<SportEvent> eventList = eventService.findAllOngoingEvents();
            if (!eventData.get(RequestParameter.EVENT_TYPE).isEmpty()) {
                if (eventService.updateEventType(eventId, SportEventType.valueOf(eventData.get(RequestParameter.EVENT_TYPE)))) {
                    session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_UPDATE_SUCCESS);
                } else {
                    session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_UPDATE_ERROR);
                }
            }
            if (!eventData.get(RequestParameter.FIRST_TEAM_NAME).isEmpty()) {
                if (eventService.updateFirstTeamName(eventId, eventData.get(RequestParameter.FIRST_TEAM_NAME))) {
                    session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_UPDATE_SUCCESS);
                } else {
                    session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_UPDATE_ERROR);
                }
            }
            if (!eventData.get(RequestParameter.SECOND_TEAM_NAME).isEmpty()) {
                if (eventService.updateSecondTeamName(eventId, eventData.get(RequestParameter.SECOND_TEAM_NAME))) {
                    session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_UPDATE_SUCCESS);
                } else {
                    session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_UPDATE_ERROR);
                }
            }
            if (!eventData.get(RequestParameter.FIRST_TEAM_RATIO).isEmpty()) {
                if (eventService.updateFirstTeamRatio(eventId, BigDecimal.valueOf(Long.parseLong(eventData.get(RequestParameter.FIRST_TEAM_RATIO))))) {
                    session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_UPDATE_SUCCESS);
                } else {
                    session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_UPDATE_ERROR);
                }
            }
            if (!eventData.get(RequestParameter.SECOND_TEAM_RATIO).isEmpty()) {
                if (eventService.updateSecondTeamRatio(eventId, BigDecimal.valueOf(Long.parseLong(eventData.get(RequestParameter.SECOND_TEAM_RATIO))))) {
                    session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_UPDATE_SUCCESS);
                } else {
                    session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_UPDATE_ERROR);
                }
            }
            if (!eventData.get(RequestParameter.EVENT_DATE).isEmpty()) {
                if (eventService.updateEventDate(eventId, LocalDate.parse(eventData.get(RequestParameter.EVENT_DATE)))) {
                    session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_UPDATE_SUCCESS);
                } else {
                    session.setAttribute(SessionAttribute.EVENT_MESSAGE, EVENT_UPDATE_ERROR);
                }
            }
            session.setAttribute(SessionAttribute.EVENTS, eventList);
        } catch (ServiceException e) {
            LOGGER.error("Exception while updating event info. " + e);
            throw new CommandException("Exception while updating event info. ", e);
        }
        return new Router(EVENTS, REDIRECT);
    }
}
