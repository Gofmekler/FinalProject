package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.UserRole;
import by.maiseichyk.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.maiseichyk.finalproject.command.PagePath.HOME;
import static by.maiseichyk.finalproject.command.PagePath.SIGN_IN;
import static by.maiseichyk.finalproject.controller.Router.Type.*;
import static by.maiseichyk.finalproject.entity.UserRole.GUEST;

public class GoToSignInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        UserRole userRole = UserRole.valueOf(String.valueOf(session.getAttribute(SessionAttribute.ROLE)));
        if (userRole.equals(GUEST)) {
            return new Router(SIGN_IN, FORWARD);
        } else {
            return new Router(HOME, FORWARD);
        }
    }
}
