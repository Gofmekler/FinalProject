package by.maiseichyk.finalproject.controller;

import java.io.*;

import by.maiseichyk.finalproject.command.CommandType;
import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.pool.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "helloServlet", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    public void init() {
        ConnectionPool.getInstance();
        LOGGER.info("Servlet init ----- " + this.getServletInfo());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        String commandStr = request.getParameter("command");
        Command command = CommandType.define(commandStr);
        try {
        Router router;
        router = command.execute(request);
        LOGGER.info("get type router " + request.getContextPath() + router.getPage());
        switch (router.getType()) {
            case FORWARD:
                request.getRequestDispatcher(router.getPage()).forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(request.getContextPath() + router.getPage());
                break;
            default:
                response.sendError(500, "Wrong router type.");
                break;
        }
//            response.sendRedirect(router.getPage());
        } catch (CommandException e) {
            request.setAttribute("error", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    public void destroy() {
        LOGGER.info("Servlet and driver are destroyed --- " + this.getServletName());
        ConnectionPool.getInstance().destroyPool();
        ConnectionPool.getInstance().deregisterDriver();
    }
}