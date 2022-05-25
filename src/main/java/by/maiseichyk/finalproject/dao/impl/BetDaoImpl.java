package by.maiseichyk.finalproject.dao.impl;

import by.maiseichyk.finalproject.dao.BaseDao;
import by.maiseichyk.finalproject.dao.BetDao;
import by.maiseichyk.finalproject.dao.mapper.impl.BetMapper;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.BetStatus;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BetDaoImpl extends BaseDao<Bet> implements BetDao {
    private static final String INSERT_NEW_BET = "INSERT INTO bets(bet_id, user_login, unique_sport_event_id, bet_date, bet_amount, bet_status) VALUES (?,?,?,?,?,?)";
    private static final String DELETE_BET = "DELETE FROM bets WHERE bet_id = ?";
    private static final String UPDATE_BET = "UPDATE bets SET(bet_date, bet_amount, bet_status) WHERE bet_id = ?";
    private static final String SELECT_BET_BY_ID = "SELECT user_login, sport_event, bet_date, bet_amount, bet_status FROM bets WHERE bet_id = ?";
    private static final String SELECT_USER_BETS_BY_USER_LOGIN = "SELECT bet_id, user_login, unique_sport_event_id, bet_date, bet_amount, bet_status FROM bets WHERE user_login = ?";
    private static final String SELECT_USER_BETS_BY_BET_STATUS = "SELECT bet_id, unique_sport_event_id, bet_date, bet_amount, FROM bets WHERE bet_status = ? AND user_login = ?";
    private static final String SELECT_BETS_BY_EVENT_ID = "SELECT bet_amount, user_login, bet_chosen_team FROM bets WHERE unique_sport_event_id = ?";
    private static final String SELECT_BETS_FOR_PROVIDED_TEAM_BY_EVENT_ID = "SELECT bet_amount, user_login FROM bets WHERE unique_sport_event_id = ? AND bet_chosen_team = ?";
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
            statement.setString(2, bet.getUserLogin());
            statement.setString(3, bet.getSportEventId());
            statement.setString(4, bet.getBetDate());
            statement.setString(5, String.valueOf(bet.getBetAmount()));
            statement.setString(6, bet.getBetStatus().toString());
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            throw new DaoException("Can't insert new user to Database: ", e);
        }
        return match;
    }

    @Override
    public boolean delete(Bet bet) throws DaoException {
        boolean match;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BET)) {
            statement.setString(1, bet.getId());
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            throw new DaoException("Can't delete event in Database. " + e);
        }
        return match;
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
    public List<Optional<Bet>> findUserBetsByBetStatus(String userLogin, BetStatus betStatus) throws DaoException {
        List betList;//todo list
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BETS_BY_BET_STATUS)) {
            preparedStatement.setString(1, userLogin);
            preparedStatement.setString(2, betStatus.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            BetMapper betMapper = BetMapper.getInstance();
            betList = betMapper.retrieve(resultSet);
        } catch (SQLException exception) {
//                LOGGER.error("Error has occurred while finding event by id: " + exception);
            throw new DaoException("Error has occurred while finding event by id: ", exception);
        }
        return null;
    }

    @Override
    public List<Bet> findAllUserBetsByLogin(String userLogin) throws DaoException {
        List<Bet> bets;
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BETS_BY_USER_LOGIN)){
            preparedStatement.setString(1, userLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            BetMapper betMapper = BetMapper.getInstance();
            bets = betMapper.retrieve(resultSet);
        } catch (SQLException e){
            throw new DaoException(e);
        }
        return bets;
    }

    @Override
    public List<Bet> findAllBetsByEventId(String eventId) throws DaoException {
        List<Bet> bets;
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BETS_BY_EVENT_ID)){
            preparedStatement.setString(1, eventId);
            ResultSet resultSet = preparedStatement.executeQuery();
            BetMapper betMapper = BetMapper.getInstance();
            bets = betMapper.retrieve(resultSet);
        } catch (SQLException e){
            throw new DaoException(e);
        }
        return bets;
    }

    @Override
    public List<Bet> findBetsForProvidedTeamByEventID(String eventId, String providedTeam) throws DaoException {
        List<Bet> bets;
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BETS_FOR_PROVIDED_TEAM_BY_EVENT_ID)){
            preparedStatement.setString(1, eventId);
            preparedStatement.setString(2, providedTeam);
            ResultSet resultSet = preparedStatement.executeQuery();
            BetMapper betMapper = BetMapper.getInstance();
            bets = betMapper.retrieve(resultSet);
        } catch (SQLException e){
            throw new DaoException(e);
        }
        return bets;
    }
}
