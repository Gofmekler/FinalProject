package by.maiseichyk.finalproject.command.impl.admin;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.dao.impl.SportEventDaoImpl;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.SportEventType;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.SportEventServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;
import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class UpdateSportEventCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        SportEventServiceImpl eventService = SportEventServiceImpl.getInstance();
        Map<String, String> eventData = new HashMap<>();
        String eventId = (String) session.getAttribute("unique_event_id");
        eventData.put(EVENT_TYPE, request.getParameter(EVENT_TYPE).toUpperCase());
        eventData.put(FIRST_TEAM, request.getParameter(FIRST_TEAM));
        eventData.put(FIRST_TEAM_RATIO, request.getParameter(FIRST_TEAM_RATIO));
        eventData.put(SECOND_TEAM, request.getParameter(SECOND_TEAM));
        eventData.put(SECOND_TEAM_RATIO, request.getParameter(SECOND_TEAM_RATIO));
        eventData.put(EVENT_DATE, request.getParameter(EVENT_DATE));
        try {
            if (!eventData.get(EVENT_TYPE).isEmpty()){
                eventService.updateEventType(eventId, SportEventType.valueOf(eventData.get(EVENT_TYPE)));
            }
            if (!eventData.get(FIRST_TEAM).isEmpty()){
                eventService.updateFirstTeamName(eventId, eventData.get(FIRST_TEAM));
            }
            if (!eventData.get(SECOND_TEAM).isEmpty()){
                eventService.updateSecondTeamName(eventId, eventData.get(SECOND_TEAM));
            }
            if (!eventData.get(FIRST_TEAM_RATIO).isEmpty()){
                eventService.updateFirstTeamRatio(eventId, BigDecimal.valueOf(Long.parseLong(eventData.get(FIRST_TEAM_RATIO))));
            }
            if (!eventData.get(SECOND_TEAM_RATIO).isEmpty()){
                eventService.updateSecondTeamRatio(eventId, BigDecimal.valueOf(Long.parseLong(eventData.get(SECOND_TEAM_RATIO))));
            }
            if (!eventData.get(EVENT_DATE).isEmpty()){
                eventService.updateEventDate(eventId, LocalDate.parse(eventData.get(EVENT_DATE)));
            }
        } catch (ServiceException e) {
            session.setAttribute("error_msg", "Exception in Event DAO. " + e);
            return new Router(ERROR_500, REDIRECT);
        }
        return new Router(EVENTS, FORWARD);
    }
}
