package by.maiseichyk.finalproject.service.impl;

import by.maiseichyk.finalproject.dao.Transaction;
import by.maiseichyk.finalproject.dao.impl.BetDaoImpl;
import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.BetStatus;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.BetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Optional;

public class BetServiceImpl implements BetService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final BetServiceImpl instance = new BetServiceImpl();

    private BetServiceImpl() {
    }

    public static BetServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean checkBalance(String userLogin, BigDecimal betAmount) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        try {
            Optional<User> user = userDao.findUserByLogin(userLogin);
            if (user.isPresent()) {
                BigDecimal userBalance = user.get().getBalance();
                if (userBalance.compareTo(betAmount) >= 0) {//|| userBalance.compareTo(betAmount) > 0) {
                    return true;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while checking users balance: " + e);
            throw new ServiceException(e);
        }
        return false;
    }

    @Override
    public boolean insertBet(Bet bet, User user) throws ServiceException {
        BetDaoImpl betDao = new BetDaoImpl(true);
        UserDaoImpl userDao = new UserDaoImpl(true);
        Transaction transaction = Transaction.getInstance();
        try {
            transaction.begin(betDao, userDao);
            if (betDao.insert(bet)) {
                BigDecimal currentBalance = user.getBalance();
                BigDecimal betAmount = bet.getBetAmount();
                BigDecimal actualBalance = currentBalance.subtract(betAmount);
                String login = user.getLogin();
                if (userDao.updateUserBalance(actualBalance, login)) {
                    transaction.commit();
                    return true;
                }
                transaction.rollback();
            }
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                LOGGER.error("Error while transaction rollback: " + ex);
            }
            LOGGER.error("Exception while inserting bet: " + e.getMessage());
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                LOGGER.error("Exception while ending transaction: " + e);
            }
        }
        return false;//todo boolean status?
    }

    @Override
    public Bet calculateBetSummary(Bet bet, int eventResult) throws ServiceException {
        if (eventResult > 0) {
            bet.setBetStatus(BetStatus.WIN);
        } else if (eventResult < 0) {
            bet.setBetStatus(BetStatus.LOST);
        } else {
            bet.setBetStatus(BetStatus.DRAWN);
        }
        return bet;
    }
}