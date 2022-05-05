package by.maiseichyk.finalproject.command.goTo;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.PagePath;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.dao.impl.SportEventDaoImpl;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class GoToEvent implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        SportEventDaoImpl eventDao = SportEventDaoImpl.getInstance();
        HttpSession session = request.getSession(false);
        Router router = new Router();
        try {
            List<SportEvent> events = eventDao.findAll();
            session.setAttribute("events", events);
            router.setPage(PagePath.EVENTS);
        } catch (DaoException e) {
            router.setPage(PagePath.ERROR_500);
            router.setType();
        }
        return router;
    }
}

