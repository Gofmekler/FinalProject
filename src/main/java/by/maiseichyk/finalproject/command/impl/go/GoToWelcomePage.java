package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.maiseichyk.finalproject.command.PagePath.WELCOME;
import static by.maiseichyk.finalproject.controller.Router.Type.REDIRECT;

public class GoToWelcomePage implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(WELCOME, REDIRECT);
    }
}
