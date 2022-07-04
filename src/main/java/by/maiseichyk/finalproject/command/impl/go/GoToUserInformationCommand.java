package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestAttribute;
import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.maiseichyk.finalproject.command.PagePath.USER_INFO;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;

public class GoToUserInformationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String login = request.getParameter(RequestParameter.LOGIN);
        try {
            Optional<User> user = userService.findUser(login);
            if (user.isPresent()) {
                request.setAttribute(RequestAttribute.USER_INFO, user.get());
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while searching users information. " + e);
            throw new CommandException("Exception while searching users information. ", e);
        }
        return new Router(USER_INFO, FORWARD);
    }
}
