package by.maiseichyk.finalproject.command.impl.bet;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.BetStatus;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.BetService;
import by.maiseichyk.finalproject.service.impl.BetServiceImpl;
import by.maiseichyk.finalproject.service.impl.SportEventServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;

public class BetCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String BET_ERROR_MESSAGE = "error.bet.add";
    private static final String BET_SUCCESS_MESSAGE = "confirm.bet.add";
    private static final String BALANCE_ERROR_MESSAGE = "error.balance.no_funds";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        SportEventServiceImpl sportEventService = SportEventServiceImpl.getInstance();
        BetService betService = BetServiceImpl.getInstance();
        String chosenTeam = request.getParameter(RequestParameter.CHOSEN_TEAM);
        BigDecimal winCoefficient;
        Map<String, String> betData = new HashMap<>();
        try {
            Optional<SportEvent> sportEvent = sportEventService.findSportEventById(request.getParameter(RequestParameter.EVENT_ID));
            if (sportEvent.isPresent()) {
                String firstTeam = sportEvent.get().getFirstTeam();
                String secondTeam = sportEvent.get().getSecondTeam();
                if (chosenTeam.equals(firstTeam)) {
                    winCoefficient = sportEvent.get().getFirstTeamRatio();
                } else if (chosenTeam.equals(secondTeam)) {
                    winCoefficient = sportEvent.get().getSecondTeamRatio();
                } else {
                    session.setAttribute(SessionAttribute.BET_ERROR, BET_ERROR_MESSAGE);
                    return new Router(BET_ERROR, REDIRECT);
                }
                betData.put(RequestParameter.EVENT_ID, request.getParameter(RequestParameter.EVENT_ID));
                betData.put(RequestParameter.LOGIN, user.getLogin());
                betData.put(RequestParameter.BET_AMOUNT, request.getParameter(RequestParameter.BET_AMOUNT));
                betData.put(RequestParameter.BET_STATUS, BetStatus.PROCESSING.toString());
                betData.put(RequestParameter.CHOSEN_TEAM, request.getParameter(RequestParameter.CHOSEN_TEAM));
                betData.put(RequestParameter.WIN_COEFFICIENT, winCoefficient.toString());
                if (betService.checkBalance(betData)) {
                    if (betService.insertBet(betData, user)) {
                        request.setAttribute(SessionAttribute.BET_ERROR, BET_SUCCESS_MESSAGE);
                        session.setAttribute(SessionAttribute.USER, user);
                        session.setAttribute(SessionAttribute.BETS, betService.findAllUserBetsByLogin(user.getLogin()));
                        return new Router(BET_SUCCESS, REDIRECT);
                    } else {
                        session.setAttribute(SessionAttribute.BET_ERROR, BET_ERROR_MESSAGE);
                        return new Router(BET_ERROR, REDIRECT);
                    }
                } else {
                    session.setAttribute(SessionAttribute.BALANCE_ERROR, BALANCE_ERROR_MESSAGE);
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while betting. " + e);
            throw new CommandException("Exception while betting. ", e);
        }
        return new Router(EVENTS, FORWARD);
    }
}