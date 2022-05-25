package by.maiseichyk.finalproject.command.impl.admin;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.entity.UserType;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;
import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class AddUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        User user = new User.UserBuilder()
                .setLogin(request.getParameter(USER_LOGIN))
                .setPassword(request.getParameter(USER_PASSWORD))
                .setName(request.getParameter(USER_NAME))
                .setLastName(request.getParameter(USER_LASTNAME))
                .setEmail(request.getParameter(USER_EMAIL))
                .setUserRole(UserType.valueOf(request.getParameter(USER_ROLE)))
                .build();
        try {
            if (userDao.insert(user)) {
                request.setAttribute("command_user_msg", "Inserted successfully");
                session.setAttribute("users", userDao.findAll());
            } else {
                request.setAttribute("command_user_msg", "Cannot insert new user");
            }
        } catch (DaoException e) {
            session.setAttribute("error_msg", "Exception in DAO " + e);
            return new Router(ERROR_500, REDIRECT);
        }
        return new Router(USERS_LIST, FORWARD);
    }
}
