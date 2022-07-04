package by.maiseichyk.finalproject.command.impl.user;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestAttribute;
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

import java.util.HashMap;
import java.util.Map;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;

public class AddUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String OCCUPIED_LOGIN_EMAIL_MESSAGE = "error.login_email_availability";
    private static final String ADD_USER_ERROR = "error.user.add";
    private static final String ADD_USER_SUCCESS = "confirm.user.add";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Map<String, String> userData = new HashMap<>();
        userData.put(RequestParameter.LOGIN, request.getParameter(RequestParameter.LOGIN));
        userData.put(RequestParameter.PASSWORD, request.getParameter(RequestParameter.PASSWORD));
        userData.put(RequestParameter.NAME, request.getParameter(RequestParameter.NAME));
        userData.put(RequestParameter.LASTNAME, request.getParameter(RequestParameter.LASTNAME));
        userData.put(RequestParameter.BIRTH_DATE, request.getParameter(RequestParameter.BIRTH_DATE));
        userData.put(RequestParameter.EMAIL, request.getParameter(RequestParameter.EMAIL));
        try {
            if (!userService.checkUserLegalAge(request.getParameter(RequestParameter.BIRTH_DATE)) ||
                    userService.isEmailOccupied(request.getParameter(RequestParameter.EMAIL)) ||
                    userService.isLoginOccupied(request.getParameter(RequestParameter.LOGIN))) {
                request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, OCCUPIED_LOGIN_EMAIL_MESSAGE);
                return new Router(USERS_LIST, FORWARD);
            }
            if (userService.registerUser(userData)) {
                session.setAttribute(SessionAttribute.USERS_MESSAGE, ADD_USER_SUCCESS);
                session.setAttribute(SessionAttribute.USERS, userService.findAllUsers());
            } else {
                session.setAttribute(SessionAttribute.USERS_MESSAGE, ADD_USER_ERROR);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while registering user: " + e);
            throw new CommandException("Exception while registering user: ", e);
        }
        return new Router(USERS_LIST, REDIRECT);
    }
}
