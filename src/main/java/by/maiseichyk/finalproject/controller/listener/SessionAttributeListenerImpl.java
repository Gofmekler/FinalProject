package by.maiseichyk.finalproject.controller.listener;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionAttributeListenerImpl implements HttpSessionAttributeListener {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        LOGGER.info("Attribute added - " + sbe.getSession().getAttribute("user_name"));
        LOGGER.info("Attribute added + " + sbe.getSession().getAttribute("current_page"));
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        LOGGER.info("Attribute Replaced / " + sbe.getSession().getAttribute("user_name"));
        LOGGER.info("Attribute Replaced / " + sbe.getSession().getAttribute("current_page"));
    }
}
