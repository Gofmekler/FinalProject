package by.maiseichyk.finalproject.command.impl.admin;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;
import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class AddUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Map<String, String> userData = new HashMap<>();
        userData.put("login", request.getParameter("login"));
        userData.put("password", request.getParameter("password"));
        userData.put("firstName", request.getParameter("first_name"));
        userData.put("lastName", request.getParameter("last_name"));
        userData.put("birthDate", request.getParameter("birth_date"));
        userData.put("email", request.getParameter("email"));
        try {
            if (!userService.checkUserAge(request.getParameter("birth_date"))) {
                request.setAttribute("list_msg", "Cannot register");
            }
            if (userService.isLoginOccupied(request.getParameter("login"))) {
                request.setAttribute("list_msg", "Cannot register");
            }
            if (userService.isEmailOccupied(request.getParameter("email"))) {
                request.setAttribute("register_msg", "Cannot register");
            }
            if (userService.registerUser(userData)) {
                session.setAttribute("list_msg", userService.findAllUsers());
//                session.setAttribute("user", user);
            } else {
                request.setAttribute("list_msg", "Cannot register");
            }
        } catch (ServiceException e) {
//            LOGGER.error("Exception while registering user: ", e);
            session.setAttribute("error", e.getMessage());
            return new Router(ERROR_500, REDIRECT);
        }
        return new Router(USERS_LIST, FORWARD);
    }
}
