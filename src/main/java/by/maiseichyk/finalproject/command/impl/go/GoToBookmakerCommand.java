package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestAttribute;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.entity.UserRole;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.maiseichyk.finalproject.command.PagePath.BOOKMAKERS_LIST;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;

public class GoToBookmakerCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String NO_USERS_MESSAGE = "users.message";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        try {
            List<User> users = userService.findUsers(UserRole.BOOKMAKER.toString());
            if (users.isEmpty()) {
                request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, NO_USERS_MESSAGE);
            } else {
                session.setAttribute(SessionAttribute.BOOKMAKERS, users);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while searching bookmakers." + e);
            throw new CommandException("Exception while searching bookmakers.", e);
        }
        return new Router(BOOKMAKERS_LIST, FORWARD);
    }
}
