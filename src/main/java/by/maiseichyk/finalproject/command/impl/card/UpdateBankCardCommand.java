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

import java.util.HashMap;
import java.util.Map;

import static by.maiseichyk.finalproject.command.PagePath.CARD_OPERATIONS;
import static by.maiseichyk.finalproject.controller.Router.Type.REDIRECT;

public class UpdateBankCardCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CARD_UPDATE_SUCCESS = "confirm.card.update";
    private static final String CARD_UPDATE_ERROR = "error.card.update";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BankCardServiceImpl cardService = BankCardServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Map<String, String> cardData = new HashMap<>();
        String cardNumber = request.getParameter(RequestParameter.CARD_NUMBER);
        cardData.put(RequestParameter.OWNER_NAME, request.getParameter(RequestParameter.OWNER_NAME));
        cardData.put(RequestParameter.EXPIRATION_DATE, request.getParameter(RequestParameter.EXPIRATION_DATE));
        try {
            if (!cardData.get(RequestParameter.OWNER_NAME).isEmpty()) {
                if (cardService.updateOwnerName(cardNumber, cardData.get(RequestParameter.OWNER_NAME))) {
                    session.setAttribute(SessionAttribute.CARD_MESSAGE, CARD_UPDATE_SUCCESS);
                } else {
                    session.setAttribute(SessionAttribute.CARD_MESSAGE, CARD_UPDATE_ERROR);
                }
            }
            if (!cardData.get(RequestParameter.EXPIRATION_DATE).isEmpty()) {
                if (cardService.updateExpirationDate(cardNumber, cardData.get(RequestParameter.EXPIRATION_DATE))) {
                    session.setAttribute(SessionAttribute.CARD_MESSAGE, CARD_UPDATE_SUCCESS);
                } else {
                    session.setAttribute(SessionAttribute.CARD_MESSAGE, CARD_UPDATE_ERROR);
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while updating card info. " + e);
            throw new CommandException("Exception while updating card info.", e);
        }
        return new Router(CARD_OPERATIONS, REDIRECT);
    }
}
