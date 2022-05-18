package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.PagePath;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.maiseichyk.finalproject.command.PagePath.SIGN_UP;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;

public class GoToSignUp implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
//        LOGGER.info("Go to account info command was called. ");
        return new Router(SIGN_UP, FORWARD);
    }
}
