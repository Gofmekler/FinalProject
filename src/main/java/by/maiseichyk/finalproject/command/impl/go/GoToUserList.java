package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.entity.UserType;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;

public class GoToUserList implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        HttpSession session = request.getSession(false);
        String role = session.getAttribute("user_role").toString();
        LOGGER.info("User role - " + session.getAttribute("user_role"));
        if (role.equals(UserType.ADMIN.toString().toUpperCase())) {
            try {
                List<User> users = userDao.findAll();
                session.setAttribute("users", users);
                return new Router(USERS_LIST, FORWARD);
            } catch (DaoException e) {
                return new Router(ERROR_500, REDIRECT);
            }
        }
        request.setAttribute("err_msg", "You don't have permission to this command.");
        return new Router(HOME, FORWARD);
    }
}
