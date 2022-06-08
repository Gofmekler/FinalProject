package by.maiseichyk.finalproject.service.impl;

import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.UserService;
import by.maiseichyk.finalproject.util.PasswordEncoder;
import by.maiseichyk.finalproject.util.validator.impl.UserValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.maiseichyk.finalproject.entity.UserType.CLIENT;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean isLoginOccupied(String login) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        try {
            Optional<User> user = userDao.findUserByLogin(login);
            return user.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isEmailOccupied(String email) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        try {
            Optional<User> user = userDao.findUserByEmail(email);
            return user.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findUser(String login, String password) throws ServiceException {//validate pass login
        UserDaoImpl userDao = new UserDaoImpl(false);
        try {
            Optional<User> user = userDao.findUserByLogin(login);
            if (user.isPresent()) {
                if(PasswordEncoder.encode(password).equals(user.get().getPassword())) {
                    return user;
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean checkUserAge(String birthDate) throws ServiceException {
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        boolean status = false;
        if (userValidator.checkDate(birthDate)){
            if(userValidator.checkAge(birthDate)){
                status = true;
            }
        }
        return status;
    }

    @Override
    public boolean identificateWinnersDrawnersLosers(List<Bet> bets, List<User> users) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
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
        UserDaoImpl userDao = new UserDaoImpl(false);
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

    @Override
    public boolean registerUser(Map<String, String> userData) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        String password = PasswordEncoder.encode(userData.get("password"));
        try {
            if (userValidator.checkUserData(userData)) {
                User user = new User.UserBuilder()
                        .setLogin(userData.get("login"))
                        .setPassword(password)
                        .setUserRole(CLIENT)
                        .setEmail(userData.get("email"))
                        .setName(userData.get("firstName"))
                        .setLastName(userData.get("lastName"))
                        .build();
                userDao.insert(user);
                return true;
            }
            return false;
        } catch (DaoException e) {
            LOGGER.info("Exception while registering user: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        return null;
    }

    @Override
    public boolean deleteUser(User user) throws ServiceException {
        return false;
    }

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
