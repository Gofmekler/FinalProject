package by.maiseichyk.finalproject.util.validator;

import java.util.Map;

/**
 * @author Maiseichyk
 * The interface Card validator.
 * @project Totalizator
 */
public interface BankCardValidator {
    /**
     * @param number the card number
     * @return boolean
     */
    boolean checkNumber(String number);

    /**
     * @param ownerName the owner name
     * @return boolean
     */
    boolean checkOwnerName(String ownerName);

    /**
     * @param expirationDate the expiration date
     * @return boolean
     */
    boolean checkExpirationDate(String expirationDate);

    /**
     * @param cvvNumber the cvv number
     * @return boolean
     */
    boolean checkCVVNumber(String cvvNumber);

    /**
     * @param cardData the card data
     * @return boolean
     */
    boolean checkCardData(Map<String, String> cardData);
}
