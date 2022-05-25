package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.PagePath;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.UserType;
import by.maiseichyk.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;
import static by.maiseichyk.finalproject.entity.UserType.GUEST;

public class GoToSignUpCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
//        LOGGER.info("Go to account info command was called. ");
        HttpSession session = request.getSession(false);
        UserType userRole = (UserType) session.getAttribute("user_role");
        if (userRole == null || userRole == GUEST){
            return new Router(SIGN_UP, FORWARD);
        } else {
            return new Router(HOME, FORWARD);
        }
    }
}
