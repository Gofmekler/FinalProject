package by.maiseichyk.finalproject.command.impl.admin;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;
import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class DeleteUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        User user = new User.UserBuilder()
                .setLogin(request.getParameter(USER_LOGIN))
                .build();
        try {
            if (userDao.delete(user)) {
                session.setAttribute("command_msg", "Deleted successfully");
            } else {
                session.setAttribute("command_msg", "Cannot delete this user");
            }
        } catch (DaoException e){
            session.setAttribute("command_msg", "Exception in DAO " + e);
            return new Router(ERROR_500, REDIRECT);
        }
        return new Router(USERS_LIST, FORWARD);
    }
}
