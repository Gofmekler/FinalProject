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

import static by.maiseichyk.finalproject.command.PagePath.ERROR_500;
import static by.maiseichyk.finalproject.command.PagePath.USERS_LIST;
import static by.maiseichyk.finalproject.dao.ColumnName.*;
import static by.maiseichyk.finalproject.dao.ColumnName.USER_ROLE;

public class UpdateUserInfoCommand implements Command {
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
            if (userDao.update(user)) {
                request.setAttribute("command_msg", "Updated successfully");
                session.setAttribute("users", userDao.findAll());
            } else {
                request.setAttribute("command_msg", "Cannot update user's info");
            }
        } catch (DaoException e) {
            session.setAttribute("error_msg", "Exception in DAO " + e);
            return new Router(ERROR_500, Router.Type.REDIRECT);
        }
        return new Router(USERS_LIST, Router.Type.FORWARD);
    }
}
