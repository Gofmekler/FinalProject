package by.maiseichyk.finalproject.service.impl;

import by.maiseichyk.finalproject.entity.BankCard;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.BankCardService;

import java.util.List;
import java.util.Optional;

public class BankCardServiceImpl implements BankCardService {
    @Override
    public List<BankCard> findAllUsersCards() throws ServiceException {
        return null;
    }

    @Override
    public boolean insertBankCard(BankCard bankCard) throws ServiceException {
        return false;
    }

    @Override
    public Optional<BankCard> findUserCardByLogin(String login) throws ServiceException {
        return Optional.empty();
    }
}
