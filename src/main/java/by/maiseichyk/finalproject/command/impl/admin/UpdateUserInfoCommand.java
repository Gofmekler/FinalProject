package by.maiseichyk.finalproject.command.impl.admin;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.UserType;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class UpdateUserInfoCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Map<String, String> userData = new HashMap<>();
        String login = (String) session.getAttribute("user_name");
        userData.put("login", request.getParameter("login"));
        userData.put("password", request.getParameter("password"));
        userData.put("firstName", request.getParameter("first_name"));
        userData.put("lastName", request.getParameter("last_name"));
        userData.put("birthDate", request.getParameter("birth_date"));
        userData.put("email", request.getParameter("email"));
        userData.put("role", request.getParameter("role"));
        try {
            if (!userData.get("login").isEmpty()) {
                if (userService.isLoginOccupied(userData.get("login"))) {
                    userService.updateUserLogin(login, userData.get("login"));
                    //set attribute new user_name
                }
            }
            if (!userData.get("password").isEmpty()) {
                userService.updateUserPass(login, userData.get("password"));
            }
            if (!userData.get("firstName").isEmpty()) {
                userService.updateUserName(login, userData.get("firstName"));
            }
            if (!userData.get("lastName").isEmpty()) {
                userService.updateUserLastName(login, userData.get("lastName"));
            }
            if (!userData.get("email").isEmpty()) {
                if (userService.isEmailOccupied(userData.get("email"))) {
                    userService.updateUserEmail(login, userData.get("email"));
                }
            }
            if (!userData.get("role").isEmpty()) {
                userService.updateUserRole(UserType.valueOf(userData.get("role")), login);
            }
        } catch (ServiceException e) {
            session.setAttribute("error_msg", "Exception in DAO " + e);
            return new Router(ERROR_500, Router.Type.REDIRECT);
        }
        return new Router(USERS_LIST, Router.Type.FORWARD);
    }
}
