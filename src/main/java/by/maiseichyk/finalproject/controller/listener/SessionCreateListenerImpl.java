package by.maiseichyk.finalproject.controller.listener;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionCreateListenerImpl implements  HttpSessionListener{
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        LOGGER.info("-----------------Session created--------------------" + se.getSession().getId());

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        LOGGER.info("-----------------Session is destroyed.----------------" + se.getSession().getId());
    }

}
