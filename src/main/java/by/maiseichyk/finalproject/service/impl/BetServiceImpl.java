package by.maiseichyk.finalproject.service.impl;

import by.maiseichyk.finalproject.dao.impl.BetDaoImpl;
import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.BetStatus;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.BetService;

import java.math.BigDecimal;
import java.util.List;

public class BetServiceImpl implements BetService {
    private static final BetServiceImpl instance = new BetServiceImpl();

    private BetServiceImpl() {
    }

    public static BetServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean checkBalance(String userLogin, BigDecimal betAmount) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            BigDecimal userBalance = userDao.getUserBalance(userLogin);
            if (userBalance.compareTo(betAmount) > 1 || userBalance.compareTo(betAmount) > 0) {
                return true;
            }
        } catch (DaoException e) {
            //todo log
            return false;
        }
        return false;
    }

    @Override
    public boolean insertBet(Bet bet, User user) throws ServiceException {
        BetDaoImpl betDao = BetDaoImpl.getInstance();
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            if (betDao.insert(bet)) {
                BigDecimal currentBalance = user.getBalance();
                BigDecimal betAmount = bet.getBetAmount();
                BigDecimal actualBalance = currentBalance.subtract(betAmount);
                String login = user.getLogin();
                if (userDao.updateUserBalance(actualBalance, login)) {
                    return true;//session attribute
                }//transaction
            }
        } catch (DaoException e) {
            //todo log
            return false;
        }
        return false;
    }

    @Override
    public Bet revealBetSummary(Bet bet, int eventResult) {
        BetDaoImpl betDao = BetDaoImpl.getInstance();
        if (eventResult > 0) {
            bet.setBetStatus(BetStatus.WIN);
        } else if (eventResult < 0) {
            bet.setBetStatus(BetStatus.LOST);
        }
        try {
            betDao.update(bet);//update status only todo
        } catch (DaoException e) {
            e.printStackTrace();//todo
        }
        return null;
    }
}