package by.maiseichyk.finalproject.service.impl;

import by.maiseichyk.finalproject.dao.impl.BetDaoImpl;
import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<User> findUser(String login, String password) throws ServiceException {//validate pass login
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            Optional<User> user = userDao.authenticate(login, password);
            if (user.isPresent()) {
                return user;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return Optional.empty();
    }

//    @Override
//    public List<User> identificateWinnersDrawnersLosers(User user, SportEvent sportEvent) throws ServiceException {
//        BetDaoImpl betDao = BetDaoImpl.getInstance();
//        UserDaoImpl userDao = UserDaoImpl.getInstance();
//        List<String> usersLogin = new ArrayList<>();
//        try {
//            List<Bet> bets = betDao.findAllBetsByEventId(sportEvent.getUniqueEventId());
//            for (Bet bet : bets) {
//                String login = bet.getUserLogin();
////                BigDecimal betAmount = bet.getBetAmount();
//                usersLogin.add(login);
//            }
//            // FIXME: 27.05.2022 todo realisation updating balances and find winners and losers
//            userDao.updateUsersBalanceByLogins(usersLogin);
//        } catch (DaoException e) {
//            e.printStackTrace();//todo logger
//        }
//        return null;
//    }

    @Override
    public boolean identificateWinnersDrawnersLosers(List<Bet> bets, List<User> users) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        for (User user : users) {
            for (Bet bet : bets) {
                BigDecimal balance = user.getBalance();
                BigDecimal betAmount = bet.getBetAmount();
                BigDecimal winCoefficient = bet.getWinCoefficient();
                switch (bet.getBetStatus()) {
                    case WIN:
                        balance = calculateUserBalanceAfterEventResult(balance, betAmount, winCoefficient, true);
                        break;
//                case LOST:break; not included, bcs gets nothing if lost the bet,  maybe only session attribute
                    case DRAWN:
                        balance = calculateUserBalanceAfterEventResult(balance, betAmount, winCoefficient, false);
                        break;//add default error todo
                }
                user.setBalance(balance);
            }
        }
        try {
            userDao.updateUsersBalance(users);
        } catch (DaoException e) {
            LOGGER.error("Exception while updating balances: " + e);
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public List<User> findUsersByLogins(List<String> logins) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        List<User> matchedUsers = new ArrayList<>();
        List<User> users;
        try {
            users = userDao.findAll();
            for (User user : users) {
                if (logins.contains(user.getLogin())) {
                    matchedUsers.add(user);
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();//todo log
        }
        return matchedUsers;
    }

    //    @Override
    private BigDecimal calculateUserBalanceAfterEventResult(BigDecimal userBalance, BigDecimal betAmount, BigDecimal teamRatio, boolean isWinner) throws ServiceException {//turn to private?
        BigDecimal winnerSum = betAmount.multiply(teamRatio);
        BigDecimal resultSum;
        if (isWinner) {//WIN OR DRAW, lost not included due to bet algorithm
            resultSum = userBalance.add(winnerSum);
        } else {
            resultSum = userBalance.add(betAmount);
        }
        return resultSum;
    }


}
