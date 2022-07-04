package by.maiseichyk.finalproject.command.impl.card;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.BankCardServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.maiseichyk.finalproject.command.PagePath.*;
import static by.maiseichyk.finalproject.command.PagePath.OPERATION_ERROR;
import static by.maiseichyk.finalproject.controller.Router.Type.REDIRECT;

public class DeleteBankCardCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CARD_DELETE_SUCCESS = "confirm.card.delete";
    private static final String CARD_DELETE_ERROR = "error.card.delete";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BankCardServiceImpl cardService = BankCardServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String cardNumber = request.getParameter(RequestParameter.CARD_NUMBER);
        try {
            if (cardService.deleteCard(cardNumber)) {
                session.setAttribute(SessionAttribute.CARD_MESSAGE, CARD_DELETE_SUCCESS);
                return new Router(OPERATION_SUCCESS, REDIRECT);
            } else {
                session.setAttribute(SessionAttribute.CARD_MESSAGE, CARD_DELETE_ERROR);
                return new Router(OPERATION_ERROR, REDIRECT);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while deleting card. " + e);
            throw new CommandException("Exception while deleting card. ", e);
        }
    }
}
