package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.command.RequestAttribute;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static by.maiseichyk.finalproject.command.PagePath.CARD_OPERATIONS;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;

public class GoToUserOperationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String NO_OPERATIONS_MESSAGE = "operations.message";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BankCardServiceImpl cardService = BankCardServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String login = String.valueOf(request.getParameter(RequestParameter.LOGIN));
        try {
            List<Map<String, BigDecimal>> operations = cardService.findUserOperations(login);
            if (operations.isEmpty()) {
                request.setAttribute(RequestAttribute.OPERATION_LIST_MESSAGE, NO_OPERATIONS_MESSAGE);
            } else {
                session.setAttribute(SessionAttribute.USER_OPERATIONS, operations);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while searching user operations. " + e);
            throw new CommandException("Exception while searching user operations. ", e);
        }
        return new Router(CARD_OPERATIONS, FORWARD);
    }
}
