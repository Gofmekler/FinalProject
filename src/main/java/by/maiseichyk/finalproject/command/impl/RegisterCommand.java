package by.maiseichyk.finalproject.command.impl;

import by.maiseichyk.finalproject.command.Command;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;

import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class RegisterCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Map<String, String> userData = new HashMap<>();
        userData.put("login", request.getParameter("login"));
        userData.put("password", request.getParameter("password"));
        userData.put("firstName", request.getParameter("first_name"));
        userData.put("lastName", request.getParameter("last_name"));
        userData.put("birthDate", request.getParameter("birth_date"));
        userData.put("email", request.getParameter("email"));
        try {
            if (!userService.checkUserAge(request.getParameter("birth_date"))) {
//                session.setAttribute();too small for that kind of games
                return new Router(SIGN_UP, FORWARD);
            }
            if (userService.isLoginOccupied(request.getParameter("login"))) {
//                session.setAttribute();
                return new Router(SIGN_UP, FORWARD);
            }
            if (userService.isEmailOccupied(request.getParameter("email"))) {
//                session.setAttribute();
                return new Router(SIGN_UP, FORWARD);
            }
            if (userService.registerUser(userData)) {
//                session.setAttribute("user_name", request.getParameter("login"));
//                session.setAttribute("user_role", user.getRole());
//                session.setAttribute("user", user);
                return new Router(SIGN_IN, FORWARD);
            } else {
                request.setAttribute("register_msg", "Cannot register");
                return new Router(WELCOME, FORWARD);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while registering user: ", e);
            session.setAttribute("error", "Can't register new user.");
            return new Router(ERROR_500, REDIRECT);
        }
    }
}
