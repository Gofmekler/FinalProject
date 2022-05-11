package by.maiseichyk.finalproject.command.impl;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.PagePath;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.entity.UserType;
import by.maiseichyk.finalproject.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        HttpSession session = request.getSession(false);
        User user = new User.UserBuilder()
                .setLogin(request.getParameter("login"))
                .setPassword(request.getParameter("pass"))
                .setName(request.getParameter("first_name"))
                .setLastName(request.getParameter("last_name"))
                .setEmail(request.getParameter("email"))
                .setUserRole(UserType.CLIENT)
                .build();
        try {
            if (userDao.insert(user)) {
                session.setAttribute("user_name", request.getParameter("login"));
                session.setAttribute("user_role", user.getRole());
                page = PagePath.HOME;
            } else {
                request.setAttribute("register_msg", "Cannot register");
                page = PagePath.WELCOME;
            }
        } catch (DaoException e) {
            page = PagePath.ERROR_500;
            session.setAttribute("error", "Can't register new user.");
        }
        router.setPage(page);
        return router;
    }
}
