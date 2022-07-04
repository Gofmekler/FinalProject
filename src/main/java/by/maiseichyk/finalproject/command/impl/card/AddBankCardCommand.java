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

import static by.maiseichyk.finalproject.command.PagePath.OPERATION_ERROR;
import static by.maiseichyk.finalproject.command.PagePath.OPERATION_SUCCESS;
import static by.maiseichyk.finalproject.controller.Router.Type.REDIRECT;

public class AddBankCardCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CARD_ADD_SUCCESS = "confirm.card.add";
    private static final String CARD_ADD_ERROR = "error.card.add";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BankCardServiceImpl cardService = BankCardServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(SessionAttribute.USER_LOGIN);
        Map<String, String> cardData = new HashMap<>();
        cardData.put(RequestParameter.CARD_NUMBER, request.getParameter(RequestParameter.CARD_NUMBER));
        cardData.put(RequestParameter.OWNER_NAME, request.getParameter(RequestParameter.OWNER_NAME));
        cardData.put(RequestParameter.CVV_NUMBER, request.getParameter(RequestParameter.CVV_NUMBER));
        cardData.put(RequestParameter.EXPIRATION_DATE, request.getParameter(RequestParameter.EXPIRATION_DATE));
        try {
            if (cardService.insertBankCard(cardData, login)) {
                session.setAttribute(SessionAttribute.CARD_MESSAGE, CARD_ADD_SUCCESS);
                return new Router(OPERATION_SUCCESS, REDIRECT);
            } else {
                session.setAttribute(SessionAttribute.CARD_MESSAGE, CARD_ADD_ERROR);
                return new Router(OPERATION_ERROR, REDIRECT);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while adding card. " + e);
            throw new CommandException("Exception while adding card. ", e);
        }
    }
}
