package by.maiseichyk.finalproject.command.impl.user;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;

public class DeleteUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_DELETE_SUCCESS = "confirm.user.delete";
    private static final String USER_DELETE_ERROR = "error.user.delete";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        UserServiceImpl userService = UserServiceImpl.getInstance();
        try {
            if (userService.deleteUser(request.getParameter(RequestParameter.LOGIN))) {
                session.setAttribute(SessionAttribute.USERS_MESSAGE, USER_DELETE_SUCCESS);
                session.setAttribute(SessionAttribute.USERS, userService.findAllUsers());
            } else {
                session.setAttribute(SessionAttribute.USERS_MESSAGE, USER_DELETE_ERROR);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while deleting user. " + e);
            throw new CommandException("Exception while deleting user. ", e);
        }
        return new Router(USERS_LIST, REDIRECT);
    }
}
