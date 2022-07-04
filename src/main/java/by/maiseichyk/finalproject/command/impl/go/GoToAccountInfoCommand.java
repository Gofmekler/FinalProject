package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.BetServiceImpl;
import by.maiseichyk.finalproject.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;

public class GoToAccountInfoCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        BetServiceImpl betService = BetServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String login = String.valueOf(session.getAttribute(SessionAttribute.USER_LOGIN));
        try {
            Optional<User> user = userService.findUser(login);
            if(user.isPresent()){
                session.setAttribute(SessionAttribute.USER, user.get());
            }
            List<Bet> bets = betService.findAllUserBetsByLogin(login);
            session.setAttribute(SessionAttribute.BETS, bets);
            return new Router(ACCOUNT_INFO, FORWARD);
        } catch (ServiceException e) {
            LOGGER.info("Exception while finding user info. " + e);
            throw new CommandException("Exception while finding user info. ", e);
        }
    }
}
