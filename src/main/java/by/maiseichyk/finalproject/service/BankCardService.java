package by.maiseichyk.finalproject.service;

import by.maiseichyk.finalproject.entity.BankCard;
import by.maiseichyk.finalproject.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface BankCardService {
    List<BankCard> findAllUsersCards() throws ServiceException;
    boolean insertBankCard(BankCard bankCard) throws ServiceException;
    Optional<BankCard> findUserCardByLogin(String login) throws ServiceException;
}
