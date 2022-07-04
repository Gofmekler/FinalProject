package by.maiseichyk.finalproject.command.impl.user;

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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;

public class UpdateUserInfoCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_UPDATE_SUCCESS_MESSAGE = "confirm.user.update";
    private static final String USER_UPDATE_ERROR_MESSAGE = "error.user.update";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Map<String, String> userData = new HashMap<>();
        String login = (String) session.getAttribute(SessionAttribute.USER_LOGIN);
        userData.put(RequestParameter.LOGIN, request.getParameter(RequestParameter.LOGIN));
        userData.put(RequestParameter.PASSWORD, request.getParameter(RequestParameter.PASSWORD));
        userData.put(RequestParameter.NAME, request.getParameter(RequestParameter.NAME));
        userData.put(RequestParameter.LASTNAME, request.getParameter(RequestParameter.LASTNAME));
        userData.put(RequestParameter.BIRTH_DATE, request.getParameter(RequestParameter.BIRTH_DATE));
        userData.put(RequestParameter.EMAIL, request.getParameter(RequestParameter.EMAIL));
        userData.put(RequestParameter.ROLE, request.getParameter(RequestParameter.ROLE));
        userData.put(RequestParameter.USER_BALANCE, request.getParameter(RequestParameter.USER_BALANCE));
        try {
            List<User> userList = userService.findAllUsers();
            if (!userData.get(RequestParameter.LOGIN).isEmpty()) {
                if (userService.isLoginOccupied(userData.get(RequestParameter.LOGIN))) {
                    if (userService.updateUserLogin(login, userData.get(RequestParameter.LOGIN))) {
                        request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, USER_UPDATE_SUCCESS_MESSAGE);
                    } else {
                        request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, USER_UPDATE_ERROR_MESSAGE);
                    }
                }
            }
            if (!userData.get(RequestParameter.PASSWORD).isEmpty()) {
                if (userService.updateUserPass(login, userData.get(RequestParameter.PASSWORD))) {
                    request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, USER_UPDATE_SUCCESS_MESSAGE);
                } else {
                    request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, USER_UPDATE_ERROR_MESSAGE);
                }
            }
            if (!userData.get(RequestParameter.NAME).isEmpty()) {
                if (userService.updateUserName(login, userData.get(RequestParameter.NAME))) {
                    request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, USER_UPDATE_SUCCESS_MESSAGE);
                } else {
                    request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, USER_UPDATE_ERROR_MESSAGE);
                }
            }
            if (!userData.get(RequestParameter.LASTNAME).isEmpty()) {
                if (userService.updateUserLastname(login, userData.get(RequestParameter.LASTNAME))) {
                    request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, USER_UPDATE_SUCCESS_MESSAGE);
                } else {
                    request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, USER_UPDATE_ERROR_MESSAGE);
                }
            }
            if (!userData.get(RequestParameter.EMAIL).isEmpty()) {
                if (userService.isEmailOccupied(userData.get(RequestParameter.EMAIL))) {
                    if (userService.updateUserEmail(login, userData.get(RequestParameter.EMAIL))) {
                        request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, USER_UPDATE_SUCCESS_MESSAGE);
                    } else {
                        request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, USER_UPDATE_ERROR_MESSAGE);
                    }
                }
            }
            if (!userData.get(RequestParameter.ROLE).isEmpty()) {
                if (userService.updateUserRole(UserRole.valueOf(userData.get(RequestParameter.ROLE)), login)) {
                    request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, USER_UPDATE_SUCCESS_MESSAGE);
                } else {
                    request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, USER_UPDATE_ERROR_MESSAGE);
                }
            }
            if (!userData.get(RequestParameter.USER_BALANCE).isEmpty()) {
                if (userService.updateUserBalance(RequestParameter.USER_BALANCE, BigDecimal.valueOf(Long.parseLong(userData.get(RequestParameter.USER_BALANCE))))) {
                    request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, USER_UPDATE_SUCCESS_MESSAGE);
                } else {
                    request.setAttribute(RequestAttribute.USERS_LIST_MESSAGE, USER_UPDATE_ERROR_MESSAGE);
                }
            }
            session.setAttribute(SessionAttribute.USERS, userList);
        } catch (ServiceException e) {
            LOGGER.error("Exception while updating user. " + e);
            throw new CommandException("Exception while updating user. ", e);
        }
        return new Router(USERS_LIST, FORWARD);
    }
}
