package by.maiseichyk.finalproject.controller.listener;

import by.maiseichyk.finalproject.pool.ConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionContextListenerImpl implements ServletContextListener{
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        ConnectionPool.getInstance();
        LOGGER.info("++++++------------Context initialized--------------------" + sce.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        ConnectionPool.getInstance().destroyPool();
        LOGGER.info("-----------------Context Destroyed--------------------" + sce.getServletContext().getContextPath());
    }

}
