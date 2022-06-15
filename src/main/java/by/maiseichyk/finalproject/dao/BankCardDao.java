package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.BankCard;
import by.maiseichyk.finalproject.exception.DaoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BankCardDao {
    Optional<BankCard> findCardByCardNumber(long cardNumber) throws DaoException;

    void updateCardBalance(long cardNumber, BigDecimal balance) throws DaoException;

    void updateOwnerName(long cardNumber, String ownerName) throws DaoException;

    void updateExpirationDate(long cardNumber, LocalDate date) throws DaoException;

    boolean insertOperation(long cardNumber, String login, BigDecimal amount) throws DaoException;

    List<Map<String, BigDecimal>> findUserOperations(String login) throws DaoException;

    List<Map<String, BigDecimal>> findAllOperations() throws DaoException;
}
