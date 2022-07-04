package by.maiseichyk.finalproject.controller.filter;

import by.maiseichyk.finalproject.command.*;
import by.maiseichyk.finalproject.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "PageRedirectSecurityFilter", urlPatterns = "/pages/*", initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class PageRedirectSecurityFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private String indexPath;
    private String errorPath;

    public void init(FilterConfig config) {
        indexPath = config.getInitParameter("INDEX_PATH");
        errorPath = PagePath.ERROR_500;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        UserRole role = UserRole.valueOf(String.valueOf(session.getAttribute(SessionAttribute.ROLE)));
        LOGGER.info("Current user role - " + role);
        if (role.equals(UserRole.GUEST)) {
            if (httpRequest.getParameter(RequestParameter.COMMAND) != null) {
                String commandType = httpRequest.getParameter(RequestParameter.COMMAND);
                Command definedCommand = CommandType.define(commandType.toUpperCase());
                if (CommandType.DEFAULT.getCommand() != definedCommand) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
                } else {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + errorPath);
                }
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
