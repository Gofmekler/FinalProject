package by.maiseichyk.finalproject.command.impl.bet;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.BetService;
import by.maiseichyk.finalproject.service.impl.BetServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.*;

public class BetCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Bet bet = (Bet) session.getAttribute("bet");//add bet retrieve
        //add bet status = processing
        BigDecimal betAmount = (BigDecimal) session.getAttribute("bet_amount");
        BetService betService = BetServiceImpl.getInstance();
        try {
            if (betService.checkBalance(user.getLogin(), betAmount)) {
                if (betService.insertBet(bet, user)) {
                    //update bet list session todo
                    return new Router(EVENTS, FORWARD);
                }
            } else {
                session.setAttribute("bet_msg", "You dont have enough money to do this operation.");
                return new Router(EVENTS, FORWARD);
            }
        } catch (ServiceException e) {
            //session + log todo
            return new Router(ERROR_500, REDIRECT);
        }
        //session + log todo
        return new Router(EVENTS, FORWARD);
    }
}
