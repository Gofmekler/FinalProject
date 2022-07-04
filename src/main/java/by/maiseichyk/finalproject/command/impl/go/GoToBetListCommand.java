package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.BetServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.maiseichyk.finalproject.command.PagePath.BET_LIST;
import static by.maiseichyk.finalproject.command.SessionAttribute.ALL_BETS;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;

public class GoToBetListCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String NO_BETS_MESSAGE = "bets.message";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BetServiceImpl betService = BetServiceImpl.getInstance();
        HttpSession session = request.getSession();
        try {
            List<Bet> bets = betService.findAllBets();
            if (bets.isEmpty()) {
                request.setAttribute(RequestAttribute.BET_LIST_MESSAGE, NO_BETS_MESSAGE);
            } else {
                session.setAttribute(ALL_BETS, bets);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while searching bets. " + e);
            throw new CommandException("Exception while searching bets. ", e);
        }
        return new Router(BET_LIST, FORWARD);
    }
}
