package by.maiseichyk.finalproject.command.impl.bet;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestAttribute;
import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.BetStatus;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.BetServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.maiseichyk.finalproject.command.PagePath.ACCOUNT_INFO;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;

public class UpdateBetCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String BET_UPDATE_SUCCESS = "confirm.bet.update";
    private static final String BET_UPDATE_ERROR = "error.bet.update";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BetServiceImpl betService = BetServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String login = request.getParameter(RequestParameter.LOGIN);
        String betId = request.getParameter(RequestParameter.BET_ID);
        Map<String, String> betData = new HashMap<>();
        betData.put(RequestParameter.EVENT_ID, request.getParameter(RequestParameter.EVENT_ID));
        betData.put(RequestParameter.LOGIN, login);
        betData.put(RequestParameter.BET_AMOUNT, request.getParameter(RequestParameter.BET_AMOUNT));
        betData.put(RequestParameter.BET_STATUS, BetStatus.PROCESSING.toString());
        betData.put(RequestParameter.CHOSEN_TEAM, request.getParameter(RequestParameter.CHOSEN_TEAM));
        betData.put(RequestParameter.WIN_COEFFICIENT, request.getParameter(RequestParameter.WIN_COEFFICIENT));
        try {
            List<Bet> betList = betService.findAllUserBetsByLogin(login);
            if (!betData.get(RequestParameter.BET_AMOUNT).isEmpty()) {
                if (betService.updateBetAmount(betId, betData.get(RequestParameter.BET_AMOUNT))) {
                    request.setAttribute(RequestAttribute.BET_UPDATE_MESSAGE, BET_UPDATE_SUCCESS);
                } else {
                    request.setAttribute(RequestAttribute.BET_UPDATE_MESSAGE, BET_UPDATE_ERROR);
                }
            }
            if (!betData.get(RequestParameter.BET_STATUS).isEmpty()) {
                if (betService.updateBetStatus(betId, betData.get(RequestParameter.BET_AMOUNT))) {
                    request.setAttribute(RequestAttribute.BET_UPDATE_MESSAGE, BET_UPDATE_SUCCESS);
                } else {
                    request.setAttribute(RequestAttribute.BET_UPDATE_MESSAGE, BET_UPDATE_ERROR);
                }
            }
            if (!betData.get(RequestParameter.WIN_COEFFICIENT).isEmpty()) {
                if (betService.updateWinCoefficient(betId, request.getParameter(RequestParameter.WIN_COEFFICIENT))) {
                    request.setAttribute(RequestAttribute.BET_UPDATE_MESSAGE, BET_UPDATE_SUCCESS);
                } else {
                    request.setAttribute(RequestAttribute.BET_UPDATE_MESSAGE, BET_UPDATE_ERROR);
                }
            }
            session.setAttribute(SessionAttribute.BETS, betList);
        } catch (ServiceException e) {
            LOGGER.error("Exception while updating bet. " + e);
            throw new CommandException("Exception while updating bet. ", e);
        }
        return new Router(ACCOUNT_INFO, FORWARD);
    }
}
