package by.maiseichyk.finalproject.service;

import by.maiseichyk.finalproject.entity.BankCard;
import by.maiseichyk.finalproject.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author Maiseichyk
 * The interface Card service.
 * @project Totalizator
 */
public interface BankCardService {

    /**
     * @param cardData the card data
     * @param login    the login
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean insertBankCard(Map<String, String> cardData, String login) throws ServiceException;

    /**
     * @param login      the login
     * @param cardNumber the card number
     * @param amount     the amount
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean withdrawToCard(String login, String cardNumber, BigDecimal amount) throws ServiceException;

    /**
     * @param login      the login
     * @param cardNumber the card number
     * @param amount     the amount
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean depositFromCard(String login, String cardNumber, BigDecimal amount) throws ServiceException;

    /**
     * @param bankCard the bank card
     * @param amount   the amount
     * @return boolean
     */
    boolean checkCardBalance(BankCard bankCard, BigDecimal amount);

    /**
     * @param cardNumber the card number
     * @param ownerName  the owner name
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateOwnerName(String cardNumber, String ownerName) throws ServiceException;

    /**
     * @param cardNumber the card number
     * @param date       the date
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean updateExpirationDate(String cardNumber, String date) throws ServiceException;

    /**
     * @param cardNumber the card number
     * @return boolean
     * @throws ServiceException the service exception
     */
    boolean deleteCard(String cardNumber) throws ServiceException;

    /**
     * @param login the login
     * @return the list of map
     * @throws ServiceException the service exception
     */
    List<Map<String, BigDecimal>> findUserOperations(String login) throws ServiceException;

    /**
     * @return the list of map
     * @throws ServiceException the service exception
     */
    List<Map<String, BigDecimal>> findAllOperations() throws ServiceException;
}
