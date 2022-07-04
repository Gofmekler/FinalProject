package by.maiseichyk.finalproject.command.impl;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.maiseichyk.finalproject.controller.Router.Type.REDIRECT;

public class ChangeLocaleCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String LANGUAGE_ENGLISH = "EN";
    private static final String LANGUAGE_RUSSIAN = "RU";
    private static final String LOCALE_ENGLISH = "en_EN";
    private static final String LOCALE_RUSSIAN = "ru_RU";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(SessionAttribute.CURRENT_PAGE);
        String language = request.getParameter(RequestParameter.LANGUAGE);
        LOGGER.info("Current language - " + language);
        switch (language) {
            case LANGUAGE_ENGLISH -> session.setAttribute(SessionAttribute.LOCALE, LOCALE_ENGLISH);
            case LANGUAGE_RUSSIAN -> session.setAttribute(SessionAttribute.LOCALE, LOCALE_RUSSIAN);
            default -> throw new CommandException("Exception while changing locale.");
        }
        session.setAttribute(SessionAttribute.LANGUAGE, language);
        return new Router(currentPage, REDIRECT);
    }
}
