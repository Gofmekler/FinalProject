package by.maiseichyk.finalproject.command.impl;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestAttribute;
import by.maiseichyk.finalproject.command.RequestParameter;
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

import java.util.Optional;

import static by.maiseichyk.finalproject.command.PagePath.SEARCH_USER;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;

public class SearchUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String NO_USER_MESSAGE = "users.message";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String firstname = request.getParameter(RequestParameter.NAME);
        String lastname = request.getParameter(RequestParameter.LASTNAME);
        String role = request.getParameter(RequestParameter.ROLE);
        String email = request.getParameter(RequestParameter.EMAIL);
        try {
            if (email.isEmpty()) {
                Optional<User> user = userService.findUser(firstname, lastname, UserRole.valueOf(role));
                if (user.isPresent()) {
                    session.setAttribute(SessionAttribute.USER_BY_NAME, user.get());
                } else {
                    request.setAttribute(RequestAttribute.USER_SEARCH_MESSAGE, NO_USER_MESSAGE);
                }
            }
            if (role.isEmpty()) {
                Optional<User> user = userService.findUser(firstname, lastname, email);
                if (user.isPresent()) {
                    session.setAttribute(SessionAttribute.USER_BY_NAME, user.get());
                } else {
                    request.setAttribute(RequestAttribute.USER_SEARCH_MESSAGE, NO_USER_MESSAGE);
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while finding user. " + e);
            throw new CommandException("Exception while finding user. " + e);
        }
        return new Router(SEARCH_USER, FORWARD);
    }
}
