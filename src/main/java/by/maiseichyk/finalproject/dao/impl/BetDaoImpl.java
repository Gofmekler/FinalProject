package by.maiseichyk.finalproject.dao.impl;

import by.maiseichyk.finalproject.dao.BaseDao;
import by.maiseichyk.finalproject.dao.BetDao;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.BetStatus;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BetDaoImpl extends BaseDao<Bet> implements BetDao {
    private static final String INSERT_NEW_BET = "INSERT INTO bets(bet_id, user, sport_event, bet_date, bet_amount, bet_status) VALUES (?,?,?,?,?,?)";
    private static final String DELETE_BET = "DELETE FROM bets WHERE bet_id = ?";
    private static final String UPDATE_BET = "UPDATE bets SET(bet_date, bet_amount, bet_status) WHERE bet_id = ?";
    private static final String SELECT_BET_BY_ID = "SELECT user, sport_event, bet_date, bet_amount, bet_status FROM bets WHERE bet_id = ?";
    private static final String SELECT_BET_BY_USER = "SELECT bet_id, user, sport_event, bet_date, bet_amount, bet_status FROM bets WHERE user = ?";
    private static BetDaoImpl instance = new BetDaoImpl();

    private BetDaoImpl() {
    }

    public static BetDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(Bet bet) throws DaoException {
        boolean match;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_BET)) {
            statement.setString(1, bet.getId());
            statement.setString(2, bet.getUser().getLogin());
            statement.setString(3, bet.getSportEvent().getUniqueEventId());
            statement.setString(4, bet.getBetDate());
            statement.setString(5, bet.getBetAmount());
            statement.setString(6, bet.getBetStatus().toString());
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            throw new DaoException("Can't insert new user to Database. " + e);
        }
        return match;
    }

    @Override
    public boolean delete(Bet bet) throws DaoException {
        return false;
    }

    @Override
    public List<Bet> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Bet bet) throws DaoException {
        return false;
    }

    @Override
    public List<Optional<Bet>> findUserBetsByBetStatus(User user, BetStatus betStatus) {
        return null;
    }
}
