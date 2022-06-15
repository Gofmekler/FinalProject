package by.maiseichyk.finalproject.service;

import by.maiseichyk.finalproject.entity.BankCard;
import by.maiseichyk.finalproject.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BankCardService {
    List<BankCard> findAllUsersCards() throws ServiceException;

    boolean insertBankCard(Map<String, String> cardData) throws ServiceException;

    boolean withdrawOnCard(String login, long cardNumber, BigDecimal amount) throws ServiceException;

    boolean depositFromCard(String login, long cardNumber, BigDecimal amount) throws ServiceException;

    boolean checkCardBalance(BankCard bankCard, BigDecimal amount);

    void updateOwnerName(long cardNumber, String ownerName) throws ServiceException;

    void updateExpirationDate(long cardNumber, LocalDate date) throws ServiceException;

    void deleteCard(long cardNumber) throws ServiceException;
}
