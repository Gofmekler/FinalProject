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
import by.maiseichyk.finalproject.util.EventResultGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.rowset.serial.SerialException;
import java.math.BigDecimal;
import java.security.Provider;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;
import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class AddSportEventCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        SportEventServiceImpl eventService = SportEventServiceImpl.getInstance();
        Map<String, String> eventData = new HashMap<>();
//        eventData.put(UNIQUE_EVENT_ID, request.getParameter(UNIQUE_EVENT_ID));
        eventData.put(EVENT_TYPE, request.getParameter(EVENT_TYPE).toUpperCase());
        eventData.put(FIRST_TEAM, request.getParameter(FIRST_TEAM));
        eventData.put(FIRST_TEAM_RATIO, request.getParameter(FIRST_TEAM_RATIO));
        eventData.put(SECOND_TEAM, request.getParameter(SECOND_TEAM));
        eventData.put(SECOND_TEAM_RATIO, request.getParameter(SECOND_TEAM_RATIO));
        eventData.put(EVENT_DATE, request.getParameter(EVENT_DATE));
        try {
            if (eventService.addEvent(eventData)) {
                request.setAttribute("command_sport_event_msg", "Inserted successfully");
                session.setAttribute("events", eventService.findAllOngoingEvents());
            } else {
                request.setAttribute("command_sport_event_msg", "Cannot insert new event");
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while adding event: " + e);
            request.setAttribute("error_msg", "Exception in DAO " + e);
            return new Router(ERROR_500, REDIRECT);
        }
        return new Router(EVENTS, FORWARD);
    }
}
