package by.maiseichyk.finalproject.command.impl.bet;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.Bet;
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
import java.util.Optional;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;

public class BetCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        SportEventServiceImpl sportEventService = SportEventServiceImpl.getInstance();
        String chosenTeam = request.getParameter("bet_chosen_team");//todo constant
        BigDecimal winCoefficient = null;
        try {
            Optional<SportEvent> sportEvent = sportEventService.findSportEventById(request.getParameter("event_id"));
            if (sportEvent.isPresent()) {
                String firstTeam = sportEvent.get().getFirstTeam();
                String secondTeam = sportEvent.get().getSecondTeam();
                if (chosenTeam.equals(firstTeam)) {
                    winCoefficient = sportEvent.get().getFirstTeamRatio();
                } else if (chosenTeam.equals(secondTeam)) {
                    winCoefficient = sportEvent.get().getSecondTeamRatio();
                } else {
                    //error maybe?
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Error while searching event by id. " + e.getMessage());
        }
        Bet bet = new Bet.BetBuilder()
                .setSportEventId(request.getParameter("event_id"))
                .setUserLogin(request.getParameter("user_login"))
                .setBetAmount(BigDecimal.valueOf(Long.parseLong(request.getParameter("bet_amount"))))
                .setBetStatus(BetStatus.PROCESSING)
                .setChosenTeam(request.getParameter("bet_chosen_team"))
                .setWinCoefficient(winCoefficient)
                .build();
//        BigDecimal betAmount = (BigDecimal) session.getAttribute("bet_amount");
        BetService betService = BetServiceImpl.getInstance();
        try {
            if (betService.checkBalance(user.getLogin(), bet.getBetAmount())) {
                if (betService.insertBet(bet, user)) {
                    request.setAttribute("bet_msg", "Success");
//                    session.setAttribute("bet_msg", "Success");
                    session.setAttribute("user", user);
                    //update bet list session todo
                    return new Router(BET_SUCCESS, FORWARD);
                } else {
                    request.setAttribute("bet_msg", "Cant bet due to error");
                    return new Router(EVENTS, FORWARD);
                }
            } else {
                request.setAttribute("bet_msg", "You dont have enough money to do this operation. ");
                return new Router(EVENTS, FORWARD);
            }
        } catch (ServiceException e) {
            //session + log todo
            return new Router(ERROR_500, REDIRECT);
        }
        //session + log todo
//        return new Router(EVENTS, FORWARD);
    }
}
