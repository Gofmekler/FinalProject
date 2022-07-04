package by.maiseichyk.finalproject.command.impl;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestAttribute;
import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.UserService;
import by.maiseichyk.finalproject.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String INVALID_DATA = "error.sign_in";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        try {
            Optional<User> user = userService.findUser(login, password);
            if (user.isPresent()) {
                session.setAttribute(SessionAttribute.USER_LOGIN, login);
                session.setAttribute(SessionAttribute.ROLE, user.get().getRole());
                session.setAttribute(SessionAttribute.USER, user.get());
                return new Router(HOME, FORWARD);
            } else {
                request.setAttribute(RequestAttribute.SIGN_IN_MESSAGE, INVALID_DATA);
                return new Router(WELCOME, FORWARD);
            }
        } catch (ServiceException e) {
            LOGGER.info("Exception in login command. " + e);
            throw new CommandException("Exception in login command. ", e);
        }
    }
}
