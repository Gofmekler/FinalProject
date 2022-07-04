package by.maiseichyk.finalproject.service.impl;

import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.dao.Transaction;
import by.maiseichyk.finalproject.dao.impl.BetDaoImpl;
import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.BetStatus;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.BetService;
import by.maiseichyk.finalproject.util.validator.impl.BetValidatorImpl;
import by.maiseichyk.finalproject.util.validator.impl.UserValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.maiseichyk.finalproject.command.RequestParameter.*;

public class BetServiceImpl implements BetService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final BetServiceImpl instance = new BetServiceImpl();

    private BetServiceImpl() {
    }

    public static BetServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean checkBalance(Map<String, String> betData) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        BetValidatorImpl betValidator = BetValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (betValidator.checkBetData(betData)) {
                Optional<User> user = userDao.findUserByLogin(betData.get(LOGIN));
                if (user.isPresent()) {
                    BigDecimal betAmount = BigDecimal.valueOf(Long.parseLong(betData.get(RequestParameter.BET_AMOUNT)));
                    BigDecimal userBalance = user.get().getBalance();
                    if (userBalance.compareTo(betAmount) >= 0) {
                        status = true;
                    }
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while checking users balance: " + e);
            throw new ServiceException("Exception while checking users balance: ", e);
        } finally {
            userDao.closeConnection();
        }
        return status;
    }

    @Override
    public boolean insertBet(Map<String, String> betData, User user) throws ServiceException {
        BetDaoImpl betDao = new BetDaoImpl(true);
        UserDaoImpl userDao = new UserDaoImpl(true);
        Transaction transaction = Transaction.getInstance();
        BetValidatorImpl betValidator = BetValidatorImpl.getInstance();
        boolean status = false;
        try {
            transaction.begin(betDao, userDao);
            if (betValidator.checkBetData(betData)) {
                Bet bet = new Bet.BetBuilder()
                        .setUserLogin(betData.get(RequestParameter.LOGIN))
                        .setBetStatus(BetStatus.valueOf(betData.get(RequestParameter.BET_STATUS)))
                        .setBetAmount(BigDecimal.valueOf(Double.parseDouble(betData.get(RequestParameter.BET_AMOUNT))))
                        .setChosenTeam(betData.get(RequestParameter.CHOSEN_TEAM))
                        .setSportEventId(betData.get(RequestParameter.EVENT_ID))
                        .setWinCoefficient(BigDecimal.valueOf(Double.parseDouble(betData.get(RequestParameter.WIN_COEFFICIENT))))
                        .build();
                if (betDao.insert(bet)) {
                    BigDecimal currentBalance = user.getBalance();
                    BigDecimal betAmount = bet.getBetAmount();
                    BigDecimal actualBalance = currentBalance.subtract(betAmount);
                    String login = user.getLogin();
                    if (userDao.updateUserBalance(login, actualBalance)) {
                        transaction.commit();
                        status = true;
                    } else {
                        transaction.rollback();
                    }
                }
            }
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                LOGGER.error("Error while transaction rollback: " + ex);
            }
            LOGGER.error("Exception while inserting bet: " + e);
            throw new ServiceException("Exception while inserting bet: ", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                LOGGER.error("Exception while ending transaction: " + e);
            }
        }
        return status;
    }

    public void calculateBetSummary(Bet bet, int eventResult) {
        if (eventResult > 0) {
            bet.setBetStatus(BetStatus.WIN);
        } else if (eventResult < 0) {
            bet.setBetStatus(BetStatus.LOST);
        } else {
            bet.setBetStatus(BetStatus.DRAWN);
        }
    }

    @Override
    public List<Bet> findAllUserBetsByLogin(String login) throws ServiceException {
        BetDaoImpl betDao = new BetDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        List<Bet> bets = new ArrayList<>();
        try {
            if (userValidator.checkLogin(login)) {
                bets = betDao.findAllUserBetsByLogin(login);
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while finding all bets by login. " + e);
            throw new ServiceException("Exception while finding all bets by login. ", e);
        } finally {
            betDao.closeConnection();
        }
        return bets;
    }

    @Override
    public List<Bet> findAllBets() throws ServiceException {
        BetDaoImpl betDao = new BetDaoImpl(false);
        List<Bet> bets;
        try {
            bets = betDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Exception while finding all bets. " + e);
            throw new ServiceException("Exception while finding all bets. ", e);
        } finally {
            betDao.closeConnection();
        }
        return bets;
    }

    @Override
    public List<Bet> findAllBetsByEventId(String eventId) throws ServiceException {
        BetDaoImpl betDao = new BetDaoImpl(false);
        List<Bet> bets;
        try {
            bets = betDao.findAllBetsByEventId(Integer.parseInt(eventId));
        } catch (DaoException e) {
            LOGGER.error("Exception while finding all bets by event id. " + e);
            throw new ServiceException("Exception while finding all bets by event id. ", e);
        } finally {
            betDao.closeConnection();
        }
        return bets;
    }

    @Override
    public List<Bet> findAllUserBetsByBetStatus(String login, String betStatus) throws ServiceException {
        BetDaoImpl betDao = new BetDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        BetValidatorImpl betValidator = BetValidatorImpl.getInstance();
        List<Bet> bets = new ArrayList<>();
        try {
            if (userValidator.checkLogin(login) && betValidator.checkBetStatus(betStatus)) {
                bets = betDao.findAllUserBetsByBetStatus(login, BetStatus.valueOf(betStatus));
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while finding all bets by status. " + e);
            throw new ServiceException("Exception while finding all bets by status. ", e);
        } finally {
            betDao.closeConnection();
        }
        return bets;
    }

    @Override
    public boolean updateWinCoefficient(String betId, String coefficient) throws ServiceException {
        BetValidatorImpl betValidator = BetValidatorImpl.getInstance();
        BetDaoImpl betDao = new BetDaoImpl(false);
        try {
            if (!betValidator.checkWinCoefficient(coefficient)) {
                return false;
            }
            if (betDao.updateWinCoefficient(betId, BigDecimal.valueOf(Long.parseLong(coefficient)))) {
                return true;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating coefficient." + e);
            throw new ServiceException("Exception while updating coefficient.", e);
        }
        return false;
    }

    @Override
    public boolean updateBetStatus(String betId, String betStatus) throws ServiceException {
        BetValidatorImpl betValidator = BetValidatorImpl.getInstance();
        BetDaoImpl betDao = new BetDaoImpl(false);
        try {
            if (!betValidator.checkBetStatus(betStatus)) {
                return false;
            }
            if (betDao.updateBetStatus(betId, BetStatus.valueOf(betStatus))) {
                return true;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating status." + e);
            throw new ServiceException("Exception while updating status.", e);
        }
        return false;
    }

    @Override
    public boolean updateBetAmount(String betId, String betAmount) throws ServiceException {
        BetValidatorImpl betValidator = BetValidatorImpl.getInstance();
        BetDaoImpl betDao = new BetDaoImpl(true);
        UserDaoImpl userDao = new UserDaoImpl(true);
        Transaction transaction = Transaction.getInstance();
        try {
            transaction.begin(betDao, userDao);
            if (!betValidator.checkBetAmount(betAmount)) {
                return false;
            }
            Optional<Bet> bet = betDao.findBet(betId);
            if (bet.isPresent()) {
                Optional<User> user = userDao.findUserByLogin(bet.get().getUserLogin());
                if (user.isPresent()) {
                    BigDecimal updatedBetAmount = BigDecimal.valueOf(Long.parseLong(betAmount));
                    BigDecimal actualBetAmount = bet.get().getBetAmount();
                    BigDecimal userBalance = user.get().getBalance();
                    String login = user.get().getLogin();
                    switch (updatedBetAmount.compareTo(actualBetAmount)) {
                        case 1 -> userBalance = userBalance.subtract(updatedBetAmount.subtract(actualBetAmount));
                        case -1 -> userBalance = userBalance.add(actualBetAmount.subtract(updatedBetAmount));
                    }
                    if (userDao.updateUserBalance(login, userBalance) && betDao.updateBetAmount(betId, updatedBetAmount)) {
                        transaction.commit();
                        return true;
                    }
                }
            }
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                LOGGER.error("Exception while rollbacking transaction. " + daoException);
            }
            LOGGER.error("Exception while updating bet amount." + e);
            throw new ServiceException("Exception while updating bet amount.", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                LOGGER.error("Exception while ending transaction. " + e);
            }
        }
        return false;
    }

    @Override
    public boolean deleteBet(String betId) throws ServiceException {
        BetDaoImpl betDao = new BetDaoImpl(true);
        UserDaoImpl userDao = new UserDaoImpl(true);
        Transaction transaction = Transaction.getInstance();
        try {
            transaction.begin(betDao, userDao);
            Optional<Bet> bet = betDao.findBet(betId);
            if (bet.isPresent()) {
                Optional<User> user = userDao.findUserByLogin(bet.get().getUserLogin());
                if (bet.get().getBetStatus().equals(BetStatus.PROCESSING)) {
                    if (user.isPresent()) {
                        BigDecimal userBalance = user.get().getBalance();
                        userBalance = userBalance.add(bet.get().getBetAmount());
                        userDao.updateUserBalance(user.get().getLogin(), userBalance);
                    }
                }
                if (betDao.delete(bet.get())) {
                    transaction.commit();
                    return true;
                }
            }
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                LOGGER.error("Exception while rollbacking transaction. " + daoException);
            }
            LOGGER.error("Exception while deleting bet. " + e);
            throw new ServiceException("Exception while deleting bet. ", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                LOGGER.error("Exception while ending transaction. " + e);
            }
        }
        return false;
    }
}