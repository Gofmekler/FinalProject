package by.maiseichyk.finalproject.util.validator;

import java.util.Map;

public interface BankCardValidator {
    boolean checkNumber(String number);

    boolean checkOwnerName(String ownerName);

    boolean checkExpirationDate(String expirationDate);

    boolean checkCVVNumber(String cvvNumber);

    boolean checkCardData(Map<String, String> cardData);
}
