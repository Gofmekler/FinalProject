package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.BankCard;
import by.maiseichyk.finalproject.exception.DaoException;

import java.util.Optional;

public interface BankCardDao {
    Optional<BankCard> findCardByCardNumber(long cardNumber) throws DaoException;
}
