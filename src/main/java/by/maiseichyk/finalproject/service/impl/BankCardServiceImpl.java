package by.maiseichyk.finalproject.service.impl;

import by.maiseichyk.finalproject.dao.Transaction;
import by.maiseichyk.finalproject.dao.impl.BankCardDaoImpl;
import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.BankCard;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.BankCardService;
import by.maiseichyk.finalproject.util.validator.impl.BankCardValidatorImpl;
import by.maiseichyk.finalproject.util.validator.impl.UserValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static by.maiseichyk.finalproject.command.RequestParameter.*;

public class BankCardServiceImpl implements BankCardService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final BankCardServiceImpl instance = new BankCardServiceImpl();

    private BankCardServiceImpl() {
    }

    public static BankCardServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insertBankCard(Map<String, String> cardData, String login) throws ServiceException {
        BankCardDaoImpl bankCardDao = new BankCardDaoImpl(true);
        Transaction transaction = Transaction.getInstance();
        BankCardValidatorImpl cardValidator = BankCardValidatorImpl.getInstance();
        try {
            if (cardValidator.checkCardData(cardData)) {
                transaction.begin(bankCardDao);
                BankCard bankCard = new BankCard.BankCardBuilder()
                        .setCardNumber(Long.parseLong(cardData.get(CARD_NUMBER)))
                        .setOwnerName(cardData.get(OWNER_NAME))
                        .setCvvNumber(Integer.parseInt(cardData.get(CVV_NUMBER)))
                        .setExpirationDate(LocalDate.parse(cardData.get(EXPIRATION_DATE)))
                        .build();
                if (bankCardDao.insert(bankCard)) {
                    if (bankCardDao.insertOperation(bankCard.getCardNumber(), login, BigDecimal.ZERO)) {
                        transaction.commit();
                        return true;
                    }
                    transaction.rollback();
                }
            }
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                LOGGER.error("Exception while rollbacking transaction." + e);
            }
            LOGGER.error("Exception while adding card." + e);
            throw new ServiceException("Exception while adding card.", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                LOGGER.error("Exception while ending transaction." + e);
            }
        }
        return false;
    }

    @Override
    public boolean withdrawToCard(String login, String cardNumber, BigDecimal amount) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(true);
        BankCardDaoImpl cardDao = new BankCardDaoImpl(true);
        UserServiceImpl userService = UserServiceImpl.getInstance();
        BankCardValidatorImpl cardValidator = BankCardValidatorImpl.getInstance();
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        Transaction transaction = Transaction.getInstance();
        try {
            if (cardValidator.checkNumber(cardNumber) && userValidator.checkLogin(login)) {
                transaction.begin(userDao, cardDao);
                Optional<User> user = userDao.findUserByLogin(login);
                long bankCardNumber = Long.parseLong(cardNumber);
                Optional<BankCard> bankCard = cardDao.findCardByCardNumber(bankCardNumber);
                if (user.isPresent() && bankCard.isPresent()) {
                    if (userService.checkUserBalance(user.get(), amount)) {
                        BigDecimal cardBalance = bankCard.get().getBalance();
                        BigDecimal userBalance = user.get().getBalance();
                        user.get().setBalance(userBalance.subtract(amount));
                        bankCard.get().setBalance(cardBalance.add(amount));
                        userDao.updateUserBalance(user.get().getLogin(), user.get().getBalance());
                        cardDao.updateCardBalance(bankCard.get().getCardNumber(), bankCard.get().getBalance());
                        if (cardDao.insertOperation(bankCardNumber, login, amount)) {
                            transaction.commit();
                            return true;
                        } else {
                            transaction.rollback();
                        }
                    }
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while withdrawing." + e);
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                LOGGER.error("Exception while rollbacking transaction." + e);
            }
            throw new ServiceException("Exception while withdrawing.", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                LOGGER.error("Exception while ending transaction." + e);
            }
        }
        return false;
    }

    @Override
    public boolean depositFromCard(String login, String cardNumber, BigDecimal amount) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(true);
        BankCardDaoImpl cardDao = new BankCardDaoImpl(true);
        BankCardValidatorImpl cardValidator = BankCardValidatorImpl.getInstance();
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        Transaction transaction = Transaction.getInstance();
        try {
            if (cardValidator.checkNumber(cardNumber) && userValidator.checkLogin(login)) {
                transaction.begin(userDao, cardDao);
                long bankCardNumber = Long.parseLong(cardNumber);
                Optional<User> user = userDao.findUserByLogin(login);
                Optional<BankCard> bankCard = cardDao.findCardByCardNumber(Long.parseLong(cardNumber));
                if (user.isPresent() && bankCard.isPresent()) {
                    if (checkCardBalance(bankCard.get(), amount)) {
                        BigDecimal cardBalance = bankCard.get().getBalance();
                        BigDecimal userBalance = user.get().getBalance();
                        bankCard.get().setBalance(cardBalance.subtract(amount));
                        user.get().setBalance(userBalance.add(amount));
                        userDao.updateUserBalance(user.get().getLogin(), user.get().getBalance());
                        cardDao.updateCardBalance(bankCard.get().getCardNumber(), bankCard.get().getBalance());
                        if (cardDao.insertOperation(bankCardNumber, login, amount.negate())) {
                            transaction.commit();
                            return true;
                        } else {
                            transaction.rollback();
                        }
                    }
                }
            }
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                LOGGER.error("Exception while rollbacking transaction." + e);
            }
            LOGGER.error("Exception while depositing." + e);
            throw new ServiceException("Exception while depositing.", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                LOGGER.error("Exception while ending transaction." + e);
            }
        }
        return false;
    }

    @Override
    public boolean checkCardBalance(BankCard bankCard, BigDecimal amount) {
        BigDecimal actualBalance = bankCard.getBalance();
        boolean status = false;
        switch (actualBalance.subtract(amount).compareTo(BigDecimal.ZERO)) {
            case 1:
            case 0:
                status = true;
                break;
            case -1:
                break;
        }
        return status;
    }

    @Override
    public boolean updateOwnerName(String cardNumber, String ownerName) throws ServiceException {
        BankCardDaoImpl cardDao = new BankCardDaoImpl(false);
        BankCardValidatorImpl cardValidator = BankCardValidatorImpl.getInstance();
        try {
            if (cardValidator.checkOwnerName(ownerName) && cardValidator.checkNumber(cardNumber)) {
                cardDao.updateOwnerName(Long.parseLong(cardNumber), ownerName);
                return true;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating owner name." + e);
            throw new ServiceException("Exception while updating owner name.", e);
        } finally {
            cardDao.closeConnection();
        }
        return false;
    }

    @Override
    public boolean updateExpirationDate(String cardNumber, String date) throws ServiceException {
        BankCardDaoImpl cardDao = new BankCardDaoImpl(false);
        BankCardValidatorImpl cardValidator = BankCardValidatorImpl.getInstance();
        try {
            if (cardValidator.checkExpirationDate(date) && cardValidator.checkNumber(cardNumber)) {
                long bankCardNumber = Long.parseLong(cardNumber);
                LocalDate localDate = LocalDate.parse(date);
                if (cardDao.updateExpirationDate(bankCardNumber, localDate)) {
                    return true;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating date." + e);
            throw new ServiceException("Exception while updating date.", e);
        } finally {
            cardDao.closeConnection();
        }
        return false;
    }

    @Override
    public boolean deleteCard(String cardNumber) throws ServiceException {
        BankCardDaoImpl cardDao = new BankCardDaoImpl(false);
        BankCardValidatorImpl cardValidator = BankCardValidatorImpl.getInstance();
        try {
            if (cardValidator.checkNumber(cardNumber)) {
                long bankCardNumber = Long.parseLong(cardNumber);
                Optional<BankCard> bankCard = cardDao.findCardByCardNumber(bankCardNumber);
                if (bankCard.isPresent()) {
                    if (cardDao.delete(bankCard.get())) {
                        return true;
                    }
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while deleting card." + e);
            throw new ServiceException("Exception while deleting card.", e);
        } finally {
            cardDao.closeConnection();
        }
        return false;
    }

    @Override
    public List<Map<String, BigDecimal>> findUserOperations(String login) throws ServiceException {
        BankCardDaoImpl cardDao = new BankCardDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        List<Map<String, BigDecimal>> operationList;
        try {
            if (userValidator.checkLogin(login)) {
                operationList = cardDao.findUserOperations(login);
                if (!operationList.isEmpty()) {
                    return operationList;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating owner name." + e);
            throw new ServiceException("Exception while searching users operations. ", e);
        }
        return null;
    }

    @Override
    public List<Map<String, BigDecimal>> findAllOperations() throws ServiceException {
        BankCardDaoImpl cardDao = new BankCardDaoImpl(false);
        List<Map<String, BigDecimal>> operationList;
        try {
            operationList = cardDao.findAllOperations();
            if (!operationList.isEmpty()) {
                return operationList;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while searching operations." + e);
            throw new ServiceException("Exception while searching operations. ", e);
        }
        return null;
    }
}
