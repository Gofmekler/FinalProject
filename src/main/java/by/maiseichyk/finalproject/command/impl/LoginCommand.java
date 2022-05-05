package by.maiseichyk.finalproject.command.impl;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.PagePath;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.entity.UserType;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.UserService;
import by.maiseichyk.finalproject.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");//string to constant todo
//        String userRole = request.getParameter("role");
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Router router = new Router();
        String page = null;
        try {
            Optional<User> user = userService.findUser(login,password);
            if (user.isPresent()) {
                session.setAttribute("user_name", login);
                session.setAttribute("user_role", user.get().getRole());
                session.setAttribute("user", user.get());
                page = PagePath.HOME;
            } else {
                request.setAttribute("login_msg", "Invalid password or login");
                page = PagePath.WELCOME;
            }
            session.setAttribute("current_page", page);
        } catch (ServiceException e) {
            e.printStackTrace();//default page maybe
        }
        router.setPage(page);
        return router;
    }
}
