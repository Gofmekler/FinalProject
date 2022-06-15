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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BankCardServiceImpl implements BankCardService {
    private static final BankCardServiceImpl instance = new BankCardServiceImpl();

    private BankCardServiceImpl() {
    }

    public static BankCardServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<BankCard> findAllUsersCards() throws ServiceException {
        BankCardDaoImpl bankCardDao = new BankCardDaoImpl(false);
        List<BankCard> bankCardList;
        try {
            bankCardList = bankCardDao.findAll();
        } catch (DaoException e) {
            //logger
            throw new ServiceException(e);
        }
        return bankCardList;
    }

    @Override
    public boolean insertBankCard(Map<String, String> cardData) throws ServiceException {
        BankCardDaoImpl bankCardDao = new BankCardDaoImpl(false);
        BankCardValidatorImpl cardValidator = BankCardValidatorImpl.getInstance();
        try {
            if (cardValidator.checkCardData(cardData)){
                BankCard bankCard = new BankCard.BankCardBuilder()
                        .setCardNumber(Long.parseLong(cardData.get("card_number")))
                        .setOwnerName(cardData.get("owner_name"))
                        .setCvvNumber(Integer.parseInt(cardData.get("cvv_number")))
                        .setExpirationDate(LocalDate.parse(cardData.get("expiration_date")))
                        .build();
                if (bankCardDao.insert(bankCard)) {
                    return true;
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return false;
    }

    @Override
    public boolean withdrawOnCard(String login, long cardNumber, BigDecimal amount) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(true);
        BankCardDaoImpl cardDao = new BankCardDaoImpl(true);
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Transaction transaction = Transaction.getInstance();
        try {
            transaction.begin(userDao, cardDao);
            Optional<User> user = userDao.findUserByLogin(login);
            Optional<BankCard> bankCard = cardDao.findCardByCardNumber(cardNumber);
            if (user.isPresent() || bankCard.isPresent()) {
                if (userService.checkUserBalance(user.get(), amount)) {
                    BigDecimal cardBalance = bankCard.get().getBalance();
                    BigDecimal userBalance = user.get().getBalance();
                    user.get().setBalance(userBalance.subtract(amount));
                    bankCard.get().setBalance(cardBalance.add(amount));
                    userDao.updateUserBalance(user.get().getLogin(), user.get().getBalance());
                    cardDao.updateCardBalance(bankCard.get().getCardNumber(), bankCard.get().getBalance());
                    transaction.commit();
                    return true;
                }
            }
        } catch (DaoException e) {
            //logger
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                //log
            }
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                //log
            }
        }
        return false;
    }

    @Override
    public boolean depositFromCard(String login, long cardNumber, BigDecimal amount) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(true);
        BankCardDaoImpl cardDao = new BankCardDaoImpl(true);
        Transaction transaction = Transaction.getInstance();
        try {
            transaction.begin(userDao, cardDao);
            Optional<User> user = userDao.findUserByLogin(login);
            Optional<BankCard> bankCard = cardDao.findCardByCardNumber(cardNumber);
            if (user.isPresent() || bankCard.isPresent()) {
                if (checkCardBalance(bankCard.get(), amount)) {
                    BigDecimal cardBalance = bankCard.get().getBalance();
                    BigDecimal userBalance = user.get().getBalance();
                    bankCard.get().setBalance(cardBalance.subtract(amount));
                    user.get().setBalance(userBalance.add(amount));
                    userDao.updateUserBalance(user.get().getLogin(), user.get().getBalance());
                    cardDao.updateCardBalance(bankCard.get().getCardNumber(), bankCard.get().getBalance());
                    transaction.commit();
                    return true;
                }
            }
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                //logger
            }
            //log todo
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                //log
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
    public void updateOwnerName(long cardNumber, String ownerName) throws ServiceException {
        BankCardDaoImpl cardDao = new BankCardDaoImpl(false);
        try {
            cardDao.updateOwnerName(cardNumber, ownerName);
        } catch (DaoException e) {
            //logger
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateExpirationDate(long cardNumber, LocalDate date) throws ServiceException {
        BankCardDaoImpl cardDao = new BankCardDaoImpl(false);
        try {
            cardDao.updateExpirationDate(cardNumber, date);
        } catch (DaoException e) {
            //logger
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteCard(long cardNumber) throws ServiceException {
        BankCardDaoImpl cardDao = new BankCardDaoImpl(false);
        try {
            Optional<BankCard> bankCard = cardDao.findCardByCardNumber(cardNumber);
            if (bankCard.isPresent()){
                cardDao.delete(bankCard.get());
            }
        } catch (DaoException e){
            //logger
            throw new ServiceException(e);
        }
    }
}
