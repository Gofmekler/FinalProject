package by.maiseichyk.finalproject.dao.impl;

import by.maiseichyk.finalproject.dao.BankCardDao;
import by.maiseichyk.finalproject.dao.BaseDao;
import by.maiseichyk.finalproject.dao.mapper.impl.BankCardMapper;
import by.maiseichyk.finalproject.entity.BankCard;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.pool.ConnectionPool;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class BankCardDaoImpl extends BaseDao<BankCard> implements BankCardDao {
    private static final String INSERT_BANKCARD = "INSERT INTO bank_cards(card_number, owner_name, cvv_number, balance, expiration_date) VALUES(?,?,?,?,?)";
    private static final String DELETE_BANKCARD = "DELETE FROM bank_cards WHERE card_number = ?";
    private static final String FIND_ALL_BANKCARDS = "SELECT card_number, owner_name, cvv_number, balance, expiration_date FROM bank_cards";
    private static final String UPDATE_BANKCARD = "UPDATE bank_cards SET(owner_name = ?, cvv_number = ?, expiration_date = ?) WHERE card_number = ?";
    private static final String UPDATE_OWNER_NAME = "UPDATE bank_cards SET owner_name = ? WHERE card_number = ?";
    private static final String UPDATE_EXPIRATION_DATE = "UPDATE bank_cards SET expiration_date = ? WHERE card_number = ?";
    private static final String UPDATE_BALANCE = "UPDATE bank_cards SET balance = ? WHERE card_number = ?";
    private static final String FIND_BANKCARD_BY_CARD_NUMBER = "SELECT card_number, owner_name, cvv_number, balance, expiration_date FROM bank_cards WHERE card_number = ?";
    private static final String FIND_ALL_OPERATIONS = "SELECT card_number, idcard_operations, operation_amount FROM bank_cards";
    private static final String FIND_USER_OPERATIONS = "SELECT card_number, idcard_operations, operation_amount FROM bank_cards WHERE login = ?";
    private static final String INSERT_OPERATION = "INSERT INTO card_operations(card_number, login, operation_amount) VALUES(?,?,?)";

    public BankCardDaoImpl() {
    }

    public BankCardDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public boolean insert(BankCard bankCard) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_BANKCARD)) {
            statement.setString(1, String.valueOf(bankCard.getCardNumber()));
            statement.setString(2, bankCard.getOwnerName());
            statement.setString(3, String.valueOf(bankCard.getCvvNumber()));
            statement.setString(4, bankCard.getBalance().toString());
            statement.setString(5, bankCard.getExpirationDate().toString());
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            throw new DaoException("Can't insert new card to Database: ", e);
        }
        return match;
    }

    @Override
    public boolean delete(BankCard bankCard) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BANKCARD)) {
            statement.setString(1, String.valueOf(bankCard.getCardNumber()));
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            throw new DaoException("Can't delete card to Database: ", e);
        }
        return match;
    }

    @Override
    public List<BankCard> findAll() throws DaoException {
        List<BankCard> bankCards;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BANKCARDS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            BankCardMapper cardMapper = BankCardMapper.getInstance();
            bankCards = cardMapper.retrieve(resultSet);
        } catch (SQLException e) {
//                LOGGER.error("Error has occurred while finding cards: " + exception);
            throw new DaoException("Error has occurred while finding cards: ", e);
        }
        return bankCards;
    }

    @Override
    public boolean update(BankCard bankCard) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BANKCARD)) {
            statement.setString(1, bankCard.getOwnerName());
            statement.setString(2, String.valueOf(bankCard.getCvvNumber()));
            statement.setString(3, bankCard.getExpirationDate().toString());
            statement.setString(4, String.valueOf(bankCard.getCardNumber()));
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            throw new DaoException("Can't update card in Database: ", e);
        }
        return match;
    }

    @Override
    public Optional<BankCard> findCardByCardNumber(long cardNumber) throws DaoException {
        List<BankCard> bankCards;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BANKCARD_BY_CARD_NUMBER)) {
            preparedStatement.setString(1, String.valueOf(cardNumber));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                BankCardMapper cardMapper = BankCardMapper.getInstance();
                bankCards = cardMapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
//                LOGGER.error("Error has occurred while finding user by login: " + exception);
            throw new DaoException("Error has occurred while finding card by number: ", e);
        }
        return bankCards.isEmpty() ? Optional.empty() : Optional.of(bankCards.get(0));
    }

    @Override
    public void updateCardBalance(long cardNumber, BigDecimal balance) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BANKCARD)) {
            statement.setString(1, balance.toString());
            statement.setString(2, String.valueOf(cardNumber));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't update card in Database: ", e);
        }
    }

    @Override
    public void updateOwnerName(long cardNumber, String ownerName) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BANKCARD)) {
            statement.setString(1, ownerName);
            statement.setString(2, String.valueOf(cardNumber));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't update card in Database: ", e);
        }
    }

    @Override
    public void updateExpirationDate(long cardNumber, LocalDate date) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BANKCARD)) {
            statement.setString(1, date.toString());
            statement.setString(2, String.valueOf(cardNumber));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't update card in Database: ", e);
        }
    }

    @Override
    public boolean insertOperation(long cardNumber, String login, BigDecimal amount) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_OPERATION)) {
            statement.setString(1, String.valueOf(cardNumber));
            statement.setString(2, login);
            statement.setString(3, amount.toString());
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            throw new DaoException("Can't insert new operation to Database: ", e);
        }
        return match;
    }

    @Override
    public List<Map<String, BigDecimal>> findUserOperations(String login) throws DaoException {
        List<Map<String, BigDecimal>> operationList = new ArrayList<>();
        Map<String, BigDecimal> operation = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_OPERATIONS)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    operation.put(resultSet.getNString(1) + resultSet.getNString(2), resultSet.getBigDecimal(3));
                }//todo fixme
                operationList.add(operation);
            }
        } catch (SQLException e) {
//                LOGGER.error("Error has occurred while finding cards: " + exception);
            throw new DaoException("Error has occurred while finding user operations: ", e);
        }
        return operationList;
    }

    @Override
    public List<Map<String, BigDecimal>> findAllOperations() throws DaoException {
        List<Map<String, BigDecimal>> operationList = new ArrayList<>();
        Map<String, BigDecimal> operation = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_OPERATIONS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                operation.put(resultSet.getNString(1) + resultSet.getNString(2), resultSet.getBigDecimal(3));
            }
            operationList.add(operation);
        } catch (SQLException e) {
//                LOGGER.error("Error has occurred while finding cards: " + exception);
            throw new DaoException("Error has occurred while finding operations: ", e);
        }
        return operationList;
    }
}
