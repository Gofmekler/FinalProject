package by.maiseichyk.finalproject.command.impl.bet;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.BetServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.controller.Router.Type.REDIRECT;

public class DeleteBetCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BetServiceImpl betService = BetServiceImpl.getInstance();
        try {
            if (betService.deleteBet(request.getParameter(RequestParameter.BET_ID))) {
                return new Router(BET_SUCCESS, REDIRECT);
            } else {
                return new Router(BET_ERROR, REDIRECT);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while deleting bet. " + e);
            throw new CommandException("Exception while deleting bet. ", e);
        }
    }
}
