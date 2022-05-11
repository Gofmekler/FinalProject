package by.maiseichyk.finalproject.controller.filter;

import by.maiseichyk.finalproject.entity.UserType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
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
    public void init(FilterConfig config) throws ServletException {
        indexPath = config.getInitParameter("INDEX_PATH");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        UserType userRole = (UserType) session.getAttribute("user_role");
        if (userRole == UserType.GUEST || userRole == null){
            session.setAttribute("register_msg", "You need to register or login firstly");//FIXME
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
            return;
        }
//        else{//checking user role
//            RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher("/pages/main.jsp");
//            requestDispatcher.forward(httpRequest, httpResponse);
//        httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);

//    }
//        httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        chain.doFilter(request, response);
    }
}
