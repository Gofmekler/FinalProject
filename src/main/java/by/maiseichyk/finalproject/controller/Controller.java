package by.maiseichyk.finalproject.controller;

import java.io.*;

import by.maiseichyk.finalproject.command.CommandType;
import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "helloServlet", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    public void init() {
        LOGGER.info("Servlet init - " + this.getServletInfo());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void destroy() {
        LOGGER.info("destroyed - " + this.getServletName());
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String commandStr = request.getParameter(RequestParameter.COMMAND);
        Command command = CommandType.define(commandStr);
        try {
            Router router;
            router = command.execute(request);
            LOGGER.info("get type router " + request.getContextPath() + router.getPage());
            switch (router.getType()) {
                case FORWARD -> request.getRequestDispatcher(router.getPage()).forward(request, response);
                case REDIRECT -> response.sendRedirect(request.getContextPath() + router.getPage());
                default -> response.sendError(500, "Wrong router type.");
            }
        } catch (CommandException e) {
            LOGGER.warn("Cant find command: ", e);
            response.sendError(500, e.getMessage());
        }
    }
}