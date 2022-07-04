package by.maiseichyk.finalproject.controller.filter;

import by.maiseichyk.finalproject.command.CommandType;
import by.maiseichyk.finalproject.command.PagePath;
import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(urlPatterns = {"/controller", "/pages/*"})
public class CurrentPageFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMAND_DELIMITER = "?";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(false);
        UserRole role = UserRole.valueOf(String.valueOf(session.getAttribute(SessionAttribute.ROLE)));
        LOGGER.info("Current user role - " + role);
        if (httpServletRequest.getParameter(RequestParameter.COMMAND) != null) {
            String commandType = httpServletRequest.getParameter(RequestParameter.COMMAND);
            if (CommandType.CHANGE_LOCALE.getCommand() != CommandType.define(commandType.toUpperCase())) {
                String currentPage = httpServletRequest.getServletPath() + COMMAND_DELIMITER + httpServletRequest.getQueryString();
                session.setAttribute(SessionAttribute.CURRENT_PAGE, currentPage);
                LOGGER.info("Current page filter - " + currentPage);
            }
        } else {
            session.setAttribute(SessionAttribute.CURRENT_PAGE, httpServletRequest.getServletPath());
        }
        chain.doFilter(request, response);
    }
}
