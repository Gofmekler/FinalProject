package by.maiseichyk.finalproject.command.impl.card;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.CommandException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.impl.BankCardServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class AddBankCardCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BankCardServiceImpl cardService = BankCardServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Map<String, String> cardData = new HashMap<>();
        cardData.put("card_number", request.getParameter("card_number"));
        cardData.put("owner_name", request.getParameter("owner_name"));
        cardData.put("cvv_number", request.getParameter("cvv_number"));
        cardData.put("expiration_date", request.getParameter("expiration_date"));
        try {
            if(cardService.insertBankCard(cardData)){
//                session.setAttribute();
            }
        } catch (ServiceException e){
            //logger
        }
        return new Router();
    }
}
