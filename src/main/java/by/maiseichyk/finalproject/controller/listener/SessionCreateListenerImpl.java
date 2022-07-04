package by.maiseichyk.finalproject.controller.listener;

import by.maiseichyk.finalproject.command.PagePath;
import by.maiseichyk.finalproject.entity.UserRole;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.maiseichyk.finalproject.command.SessionAttribute.*;

@WebListener
public class SessionCreateListenerImpl implements HttpSessionListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DEFAULT_LOCALE = "en_EN";
    private static final String DEFAULT_LANGUAGE = "EN";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        LOGGER.info("Session created - " + se.getSession().getId());
        HttpSession session = se.getSession();
        session.setAttribute(CURRENT_PAGE, PagePath.WELCOME);
        session.setAttribute(LOCALE, DEFAULT_LOCALE);
        session.setAttribute(LANGUAGE, DEFAULT_LANGUAGE);
        session.setAttribute(ROLE, UserRole.GUEST.toString());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        LOGGER.info("Session is destroyed. - " + se.getSession().getId());
    }
}
