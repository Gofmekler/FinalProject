package by.maiseichyk.finalproject.command.goTo;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.PagePath;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoToSignIn implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
//        LOGGER.info("Go to account info command was called. ");
        return new Router(PagePath.SIGN_IN, Router.Type.FORWARD);
    }
}
