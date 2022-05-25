package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.UserType;
import by.maiseichyk.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.maiseichyk.finalproject.command.PagePath.HOME;
import static by.maiseichyk.finalproject.command.PagePath.SIGN_IN;
import static by.maiseichyk.finalproject.controller.Router.Type.*;
import static by.maiseichyk.finalproject.entity.UserType.GUEST;

public class GoToSignInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
//        LOGGER.info("Go to account info command was called. ");
        HttpSession session = request.getSession(false);
        UserType userRole = (UserType) session.getAttribute("user_role");
        if (userRole == null || userRole == GUEST){
            return new Router(SIGN_IN, FORWARD);
        } else {
            return new Router(HOME, FORWARD);
        }
    }
}
