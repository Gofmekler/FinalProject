package by.maiseichyk.finalproject.command.impl;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.PagePath;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.entity.UserType;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.UserService;
import by.maiseichyk.finalproject.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;

public class LoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");//string to constant todo
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession(false);
        try {
            Optional<User> user = userService.findUser(login,password);
            if (user.isPresent()) {
                session.setAttribute("user_name", login);
                session.setAttribute("user_role", user.get().getRole());
                session.setAttribute("user", user.get());
                return new Router(HOME, FORWARD);
            } else {
                request.setAttribute("login_msg", "Invalid password or login");
                return new Router(WELCOME, FORWARD);
            }
        } catch (ServiceException e) {
            //log todo
            return new Router(ERROR_500, REDIRECT);
        }
    }
}
