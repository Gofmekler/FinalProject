package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.BankCard;
import by.maiseichyk.finalproject.exception.DaoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * @project Totalizator
 * @author Maiseichyk
 * The interface BankCardDao.
 */
public interface BankCardDao {
    /**
     * @param cardNumber the card number
     * @return optional bank card
     * @throws DaoException the dao exception
     */
    Optional<BankCard> findCardByCardNumber(long cardNumber) throws DaoException;

    /**
     * @param cardNumber the card number
     * @param balance    the balance
     * @throws DaoException the dao exception
     */
    void updateCardBalance(long cardNumber, BigDecimal balance) throws DaoException;

    /**
     * @param cardNumber the card number
     * @param ownerName  the owner name
     * @throws DaoException the dao exception
     */
    void updateOwnerName(long cardNumber, String ownerName) throws DaoException;

    /**
     * @param cardNumber the card number
     * @param date       the local date
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean updateExpirationDate(long cardNumber, LocalDate date) throws DaoException;

    /**
     * @param cardNumber the card number
     * @param login      the login
     * @param amount     the amount
     * @return boolean
     * @throws DaoException the dao exception
     */
    boolean insertOperation(long cardNumber, String login, BigDecimal amount) throws DaoException;

    /**
     * @param login the login
     * @return list of map
     * @throws DaoException the dao exception
     */
    List<Map<String, BigDecimal>> findUserOperations(String login) throws DaoException;

    /**
     * @return list of map
     * @throws DaoException the dao exception
     */
    List<Map<String, BigDecimal>> findAllOperations() throws DaoException;
}
