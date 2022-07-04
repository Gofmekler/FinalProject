package by.maiseichyk.finalproject.command.impl;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestAttribute;
import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.BetServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static by.maiseichyk.finalproject.command.PagePath.BET_LIST;
import static by.maiseichyk.finalproject.command.PagePath.SEARCH_BET;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;

public class SearchBetCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String NO_BETS_MESSAGE = "bets.message";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BetServiceImpl betService = BetServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String login = String.valueOf(session.getAttribute(SessionAttribute.USER_LOGIN));
        String betStatus = request.getParameter(RequestParameter.BET_STATUS);
        String eventId = request.getParameter(RequestParameter.EVENT_ID);
        List<Bet> bets = new ArrayList<>();
        try {
            if (!login.isEmpty() && !betStatus.isEmpty()) {
                bets = betService.findAllUserBetsByBetStatus(login, betStatus);
            }
            if (!eventId.isEmpty()) {
                bets = betService.findAllBetsByEventId(eventId);
            }
            if (bets.isEmpty()) {
                request.setAttribute(RequestAttribute.BET_SEARCH_MESSAGE, NO_BETS_MESSAGE);
            } else {
                session.setAttribute(SessionAttribute.ALL_BETS, bets);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while searching bet/s. " + e);
            throw new CommandException("Exception while searching bet/s. ", e);
        }
        return new Router(SEARCH_BET, FORWARD);
    }
}
