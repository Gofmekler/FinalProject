package by.maiseichyk.finalproject.command.goTo;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.PagePath;
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

public class GoToUserList implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        HttpSession session = request.getSession();
        String role = session.getAttribute("user_role").toString();
        LOGGER.info("User role - " + session.getAttribute("user_role"));
        if (role.toUpperCase().equals(UserType.ADMIN.getValue().toUpperCase())) {
            try {
                List<User> users = userDao.findAll();
                session.setAttribute("users", users);
                return new Router(PagePath.USERS_LIST, Router.Type.FORWARD);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("err_msg", "You don't have permission to this command.");
        return new Router(PagePath.HOME, Router.Type.FORWARD);
    }
}
