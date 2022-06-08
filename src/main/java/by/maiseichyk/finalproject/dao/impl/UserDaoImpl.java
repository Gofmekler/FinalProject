package by.maiseichyk.finalproject.dao.impl;

import by.maiseichyk.finalproject.dao.BaseDao;
import by.maiseichyk.finalproject.dao.UserDao;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.dao.mapper.impl.UserMapper;
import by.maiseichyk.finalproject.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

import static by.maiseichyk.finalproject.dao.ColumnName.USER_BALANCE;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FIND_USER_BY_LOGIN = "SELECT login, password, firstName, lastName, email, role, balance FROM users WHERE login = ?";
    private static final String SELECT_USER_BY_EMAIL = "SELECT login, password, firstName, lastName, email, role, balance FROM users WHERE email = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE login = ?";
    private static final String INSERT_USER = "INSERT INTO users(login,password,firstName,lastName,email,role) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_USER = "UPDATE users SET lastName = ?, firstName = ?, email = ?, password = ?, role = ? WHERE login = ?";
    private static final String UPDATE_USER_BALANCE = "UPDATE users SET balance = ? WHERE login = ?";
    private static final String SELECT_ALL_USERS = "SELECT login, password, firstName, lastName, email, role, balance FROM users";
    private static final String SELECT_USER_BALANCE = "SELECT balance FROM users WHERE login = ?";
    private static final String UPDATE_USERS_BALANCE = "UPDATE users SET balance = ? WHERE login = ?";

    public UserDaoImpl() {
    }

    public UserDaoImpl (boolean isTransaction) {
        if(!isTransaction){
            connection = ConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public boolean insert(User user) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getRole().toString());
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            throw new DaoException("Can't insert new user to Database: ", e);
        }
        return match;
    }

    @Override
    public boolean delete(User user) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setString(1, user.getLogin());
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            throw new DaoException("Can't delete user to Database: ", e);
        }
        return match;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            UserMapper userMapper = UserMapper.getInstance();
            users = userMapper.retrieve(resultSet);
        } catch (SQLException e) {
//                LOGGER.error("Error has occurred while finding users: " + exception);
            throw new DaoException("Error has occurred while finding users: ", e);
        }
        return users;
    }

    @Override
    public boolean update(User user) throws DaoException {
        boolean status;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getRole().toString());
            statement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            throw new DaoException("Can't update user info to Database: ", e);
        }
        return status;
    }

    @Override
    public BigDecimal getUserBalance(String login) throws DaoException {
        BigDecimal userBalance;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_BALANCE)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                userBalance = resultSet.getBigDecimal(USER_BALANCE);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get users balance: ", e);
        }
        return userBalance;
    }

    @Override
    public boolean updateUserBalance(BigDecimal balance, String login) throws DaoException {
        boolean status;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_BALANCE)) {
            statement.setString(1, balance.toString());
            statement.setString(2, login);
            statement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            throw new DaoException("Can't update user info to Database: ", e);
        }
        return status;
    }

    @Override
    public boolean updateUsersBalance(List<User> users) throws DaoException {
        boolean status;
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_BALANCE)) {
            for (User user : users) {
                preparedStatement.setString(1, user.getBalance().toString());
                preparedStatement.setString(2, user.getLogin());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            status = true;
        } catch (SQLException e) {
            LOGGER.error("Exception while updating users balances: " + e);
            throw new DaoException(e);
        }
        return status;
    }

    @Override
    public boolean updateUsersBalanceByLogins(List<User> users) throws DaoException {//updating todo change
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_BALANCE)) {
            for (User user : users) {
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN)) {//new sql call create
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
//                LOGGER.error("Error has occurred while finding user by login: " + exception);
            throw new DaoException("Error has occurred while finding user by login: ", e);
        }
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
//                LOGGER.error("Error has occurred while finding user by login: " + exception);
            throw new DaoException("Error has occurred while finding user by email: ", e);
        }
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
}
