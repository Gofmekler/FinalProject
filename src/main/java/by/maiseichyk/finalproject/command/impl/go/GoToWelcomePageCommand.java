package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import jakarta.servlet.http.HttpServletRequest;

import static by.maiseichyk.finalproject.command.PagePath.WELCOME;
import static by.maiseichyk.finalproject.controller.Router.Type.REDIRECT;

public class GoToWelcomePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(WELCOME, REDIRECT);
    }
}
