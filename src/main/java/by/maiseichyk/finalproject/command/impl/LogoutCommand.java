package by.maiseichyk.finalproject.command.impl;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.maiseichyk.finalproject.command.PagePath.WELCOME;
import static by.maiseichyk.finalproject.controller.Router.Type.REDIRECT;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("Invalidate session");
        request.getSession().invalidate();
        return new Router(WELCOME, REDIRECT);
    }
}
