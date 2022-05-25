package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.dao.impl.BetDaoImpl;
import by.maiseichyk.finalproject.dao.impl.SportEventDaoImpl;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.UserType;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;

public class GoToEventCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        SportEventDaoImpl eventDao = SportEventDaoImpl.getInstance();
        HttpSession session = request.getSession(false);
        try {
            List<SportEvent> events = eventDao.findAll();
            session.setAttribute("events", events);
            return new Router(EVENTS, FORWARD);
        } catch (DaoException e) {
            return new Router(ERROR_500, REDIRECT);
        }
    }
}
