package by.maiseichyk.finalproject.command.impl;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.PagePath;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.UserType;
import jakarta.servlet.http.HttpServletRequest;

import static by.maiseichyk.finalproject.command.PagePath.HOME;
import static by.maiseichyk.finalproject.command.PagePath.WELCOME;
import static by.maiseichyk.finalproject.controller.Router.Type.REDIRECT;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return new Router(WELCOME, REDIRECT);
    }
}
