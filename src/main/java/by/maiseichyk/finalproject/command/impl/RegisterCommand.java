package by.maiseichyk.finalproject.command.impl;

import by.maiseichyk.finalproject.command.Command;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;
import static by.maiseichyk.finalproject.dao.ColumnName.*;

import by.maiseichyk.finalproject.command.RequestAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class RegisterCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String REGISTER_ERROR = "error.sign_up";
    private static final String REGISTER_SUCCESS = "confirm.sign_up";
    private static final String INVALID_DATA = "error.login_email_availability";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Map<String, String> userData = new HashMap<>();
        userData.put(USER_LOGIN, request.getParameter(USER_LOGIN));
        userData.put(USER_PASSWORD, request.getParameter(USER_PASSWORD));
        userData.put(USER_NAME, request.getParameter(USER_NAME));
        userData.put(USER_LASTNAME, request.getParameter(USER_LASTNAME));
        userData.put(USER_BIRTH_DATE, request.getParameter(USER_BIRTH_DATE));
        userData.put(USER_EMAIL, request.getParameter(USER_EMAIL));
        try {
            if (!userService.checkUserLegalAge(request.getParameter(USER_BIRTH_DATE)) ||
                    userService.isEmailOccupied(request.getParameter(USER_EMAIL)) ||
                    userService.isLoginOccupied(request.getParameter(USER_LOGIN))) {
                request.setAttribute(RequestAttribute.SIGN_UP_MESSAGE, INVALID_DATA);
                return new Router(SIGN_UP, FORWARD);
            }
            if (userService.registerUser(userData)) {
                request.setAttribute(RequestAttribute.SIGN_IN_MESSAGE, REGISTER_SUCCESS);
                return new Router(SIGN_IN, FORWARD);
            } else {
                request.setAttribute(RequestAttribute.SIGN_UP_MESSAGE, REGISTER_ERROR);
                return new Router(SIGN_UP, FORWARD);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while registering user: " + e);
            throw new CommandException("Exception while registering user: ", e);
        }
    }
}
