package by.maiseichyk.finalproject.service.impl;

import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.dao.Transaction;
import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.entity.UserRole;
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

import static by.maiseichyk.finalproject.entity.UserRole.CLIENT;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean isLoginOccupied(String login) throws ServiceException {
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        UserDaoImpl userDao = new UserDaoImpl(false);
        try {
            if (userValidator.checkLogin(login)) {
                Optional<User> user = userDao.findUserByLogin(login);
                return user.isPresent();
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while checking login: " + e);
            throw new ServiceException("Exception while checking login: ", e);
        } finally {
            userDao.closeConnection();
        }
        return false;
    }

    @Override
    public boolean isEmailOccupied(String email) throws ServiceException {
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        UserDaoImpl userDao = new UserDaoImpl(false);
        try {
            if (userValidator.checkEmail(email)) {
                Optional<User> user = userDao.findUserByEmail(email);
                return user.isPresent();
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while checking email: " + e);
            throw new ServiceException("Exception while checking email: ", e);
        } finally {
            userDao.closeConnection();
        }
        return false;
    }

    @Override
    public Optional<User> findUser(String login, String password) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        try {
            Optional<User> user = userDao.findUserByLogin(login);
            if (user.isPresent()) {
                if (PasswordEncoder.encode(password).equals(user.get().getPassword())) {
                    return user;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while searching user: " + e);
            throw new ServiceException("Exception while searching user: ", e);
        } finally {
            userDao.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String login) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        try {
            if (userValidator.checkLogin(login)) {
                Optional<User> user = userDao.findUserByLogin(login);
                if (user.isPresent()) {
                    return user;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while searching user by login: " + e);
            throw new ServiceException("Exception while searching user by login: ", e);
        } finally {
            userDao.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String name, String lastname, UserRole role) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        try {
            if (userValidator.checkName(name) && userValidator.checkSurname(lastname) && userValidator.checkRole(role.toString())) {
                Optional<User> user = userDao.findUserByFullNameAndRole(name, lastname, role);
                if (user.isPresent()) {
                    return user;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while searching user by name and role: " + e);
            throw new ServiceException("Exception while searching user by name and role: ", e);
        } finally {
            userDao.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String name, String lastname, String email) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        try {
            if (userValidator.checkName(name) && userValidator.checkSurname(lastname) && userValidator.checkEmail(email)) {
                Optional<User> user = userDao.findUserByFullNameAndEmail(name, lastname, email);
                if (user.isPresent()) {
                    return user;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while searching user by name and email: " + e);
            throw new ServiceException("Exception while searching user by name and email: ", e);
        } finally {
            userDao.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public boolean checkUserLegalAge(String birthDate) throws ServiceException {
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        boolean status = false;
        if (userValidator.checkDate(birthDate)) {
            if (userValidator.checkLegalAge(birthDate)) {
                status = true;
            }
        }
        return status;
    }

    @Override
    public boolean identificateWinnersDrawnersLosers(List<Bet> bets, List<User> users) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(true);
        Transaction transaction = Transaction.getInstance();
        for (User user : users) {
            for (Bet bet : bets) {
                BigDecimal balance = user.getBalance();
                BigDecimal betAmount = bet.getBetAmount();
                BigDecimal winCoefficient = bet.getWinCoefficient();
                switch (bet.getBetStatus()) {
                    case WIN -> balance = calculateUserBalanceAfterEventResult(balance, betAmount, winCoefficient, true);
                    case DRAWN -> balance = calculateUserBalanceAfterEventResult(balance, betAmount, winCoefficient, false);
                }
                user.setBalance(balance);
            }
        }
        try {
            transaction.begin(userDao);
            userDao.updateUsersBalance(users);
            return true;
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                LOGGER.error("Exception while rollbacking transaction. " + ex);
            }
            LOGGER.error("Exception while updating balances: " + e);
            throw new ServiceException("Exception while updating balances: ", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                LOGGER.error("Exception while ending transaction. " + e);
            }
        }
    }

    @Override
    public List<User> findUsers(List<String> logins) throws ServiceException {
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
            LOGGER.error("Exception while finding user: " + e);
            throw new ServiceException("Exception while finding user: ", e);
        } finally {
            userDao.closeConnection();
        }
        return matchedUsers;
    }

    @Override
    public List<User> findUsers(String role) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        List<User> matchedUsers = new ArrayList<>();
        List<User> users;
        try {
            if (userValidator.checkRole(role)) {
                UserRole userRole = UserRole.valueOf(role);
                users = userDao.findUsersByRole(userRole);
                for (User user : users) {
                    if (user.getRole().equals(userRole)) {
                        matchedUsers.add(user);
                    }
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while finding user by role: " + e);
            throw new ServiceException("Exception while finding user by role: ", e);
        } finally {
            userDao.closeConnection();
        }
        return matchedUsers;
    }

    @Override
    public boolean registerUser(Map<String, String> userData) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        String password = PasswordEncoder.encode(userData.get(RequestParameter.PASSWORD));
        try {
            if (userValidator.checkUserData(userData)) {
                User user = new User.UserBuilder()
                        .setLogin(userData.get(RequestParameter.LOGIN))
                        .setPassword(password)
                        .setUserRole(CLIENT)
                        .setEmail(userData.get(RequestParameter.EMAIL))
                        .setName(userData.get(RequestParameter.NAME))
                        .setLastname(userData.get(RequestParameter.LASTNAME))
                        .build();
                userDao.insert(user);
                return true;
            }
            return false;
        } catch (DaoException e) {
            LOGGER.info("Exception while registering user: " + e);
            throw new ServiceException("Exception while registering user: ", e);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        List<User> users;
        UserDaoImpl userDao = new UserDaoImpl(false);
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            LOGGER.info("Exception while searching all users: " + e);
            throw new ServiceException("Exception while searching all users: ", e);
        } finally {
            userDao.closeConnection();
        }
        return users;
    }

    @Override
    public boolean checkUserBalance(User user, BigDecimal amount) {
        BigDecimal actualBalance = user.getBalance();
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
    public boolean deleteUser(String login) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (userValidator.checkLogin(login)) {
                Optional<User> user = userDao.findUserByLogin(login);
                if (user.isPresent()) {
                    if (userDao.delete(user.get())) {
                        status = true;
                    }
                }
            }
        } catch (DaoException e) {
            LOGGER.info("Exception while deleting user: " + e);
            throw new ServiceException("Exception while deleting user: ", e);
        }
        return status;
    }

    @Override
    public boolean updateUserRole(UserRole userRole, String login) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (userValidator.checkLogin(login) && userValidator.checkRole(userRole.toString())) {
                Optional<User> user = userDao.findUserByLogin(login);
                if (user.isPresent()) {
                    user.get().setRole(userRole);
                    if (userDao.updateUserRole(user.get())) {
                        status = true;
                    }
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating users role: " + e);
            throw new ServiceException("Exception while updating users role: ", e);
        } finally {
            userDao.closeConnection();
        }
        return status;
    }

    @Override
    public boolean updateUserLogin(String login, String changeTo) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (userValidator.checkLogin(changeTo)) {
                if (userDao.updateUserLogin(login, changeTo)) {
                    status = true;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating users login: " + e);
            throw new ServiceException("Exception while updating users login: ", e);
        } finally {
            userDao.closeConnection();
        }
        return status;
    }

    @Override
    public boolean updateUserPass(String login, String password) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (userValidator.checkPassword(password)) {
                if (userDao.updateUserPass(login, password)) {
                    status = true;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating users password: " + e);
            throw new ServiceException("Exception while updating users password: ", e);
        } finally {
            userDao.closeConnection();
        }
        return status;
    }

    @Override
    public boolean updateUserName(String login, String name) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (userValidator.checkName(name)) {
                if (userDao.updateUserName(login, name)) {
                    status = true;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating users name: " + e);
            throw new ServiceException("Exception while updating users name: ", e);
        } finally {
            userDao.closeConnection();
        }
        return status;
    }

    @Override
    public boolean updateUserLastname(String login, String lastname) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (userValidator.checkSurname(lastname)) {
                if (userDao.updateUserLastName(login, lastname)) {
                    status = true;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating users last name: " + e);
            throw new ServiceException("Exception while updating users last name: ", e);
        } finally {
            userDao.closeConnection();
        }
        return status;
    }

    @Override
    public boolean updateUserEmail(String login, String email) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (userValidator.checkEmail(email)) {
                if (userDao.updateUserEmail(login, email)) {
                    status = true;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating users email: " + e);
            throw new ServiceException("Exception while updating users email: ", e);
        } finally {
            userDao.closeConnection();
        }
        return status;
    }

    @Override
    public boolean updateUserBalance(String login, BigDecimal balance) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl(false);
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        boolean status = false;
        try {
            if (userValidator.checkBalance(balance.toString())) {
                if (userDao.updateUserBalance(login, balance)) {
                    status = true;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while updating users balance: " + e);
            throw new ServiceException("Exception while updating users balance: ", e);
        } finally {
            userDao.closeConnection();
        }
        return status;
    }

    private BigDecimal calculateUserBalanceAfterEventResult(BigDecimal userBalance, BigDecimal betAmount, BigDecimal teamRatio, boolean isWinner) {
        BigDecimal winnerSum = betAmount.multiply(teamRatio);
        BigDecimal resultSum;
        if (isWinner) {
            resultSum = userBalance.add(winnerSum);
        } else {
            resultSum = userBalance.add(betAmount);
        }
        return resultSum;
    }
}
