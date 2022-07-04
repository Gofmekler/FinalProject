package by.maiseichyk.finalproject.command.impl;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import jakarta.servlet.http.HttpServletRequest;

import static by.maiseichyk.finalproject.command.PagePath.ERROR_404;
import static by.maiseichyk.finalproject.controller.Router.Type.REDIRECT;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(ERROR_404, REDIRECT);
    }
}
