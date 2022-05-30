package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.dao.impl.BetDaoImpl;
import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;
import static by.maiseichyk.finalproject.controller.Router.Type.REDIRECT;

public class GoToAccountInfoCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        BetDaoImpl betDao = BetDaoImpl.getInstance();
        HttpSession session = request.getSession(false);
        String login = (String) session.getAttribute("user_name");
        try {
            Optional<User> user = userDao.findUserByLogin(login);//todo pass encode check service
            user.ifPresent(value -> session.setAttribute("user", value));
            List<Bet> bets = betDao.findAllUserBetsByLogin(login);
            session.setAttribute("bets", bets);
            return new Router(ACCOUNT_INFO, FORWARD);
        } catch (DaoException e) {
            LOGGER.info(e.getMessage());
            return new Router(ERROR_500, REDIRECT);
        }
//        LOGGER.info("Go to account info command was called. ");
//        return new Router(ACCOUNT_INFO, FORWARD);
    }
}
