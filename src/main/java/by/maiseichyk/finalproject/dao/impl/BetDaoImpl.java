package by.maiseichyk.finalproject.dao.impl;

import by.maiseichyk.finalproject.dao.BaseDao;
import by.maiseichyk.finalproject.dao.BetDao;
import by.maiseichyk.finalproject.dao.mapper.impl.BetMapper;
import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.BetStatus;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static by.maiseichyk.finalproject.entity.BetStatus.PROCESSING;

public class BetDaoImpl extends BaseDao<Bet> implements BetDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String INSERT_NEW_BET = "INSERT INTO bets(login, unique_event_id, bet_amount, bet_status, win_coefficient, bet_chosen_team) VALUES (?,?,?,?,?,?)";
    private static final String DELETE_BET = "DELETE FROM bets WHERE id_bet = ?";
    private static final String SELECT_ALL_BETS = "SELECT id_bet, login, unique_event_id, bet_amount, bet_status, bet_chosen_team, win_coefficient FROM bets";
    private static final String SELECT_USER_BETS_BY_USER_LOGIN = "SELECT id_bet,login, unique_event_id, bet_amount, bet_status, bet_chosen_team, win_coefficient FROM bets WHERE login = ?";
    private static final String SELECT_BETS_BY_EVENT_ID = "SELECT id_bet, login, unique_event_id, bet_amount, bet_status, bet_chosen_team, win_coefficient FROM bets WHERE unique_event_id = ?";
    private static final String SELECT_BETS_FOR_PROVIDED_TEAM_BY_EVENT_ID = "SELECT id_bet, login, unique_event_id, bet_amount, bet_status, bet_chosen_team, win_coefficient FROM bets WHERE unique_event_id = ? AND bet_chosen_team = ? AND bet_status = ?";
    private static final String UPDATE_BETS_STATUS = "UPDATE bets SET bet_status = ? WHERE login = ? AND id_bet = ?";
    private static final String UPDATE_BET_STATUS = "UPDATE bets SET bet_status = ? WHERE login = ? AND id_bet = ?";
    private static final String FIND_USER_BETS_BY_BET_STATUS = "SELECT id_bet, login, unique_event_id, bet_amount, bet_status, bet_chosen_team, win_coefficient FROM bets WHERE login = ? AND bet_status = ?";
    private static final String UPDATE_BET_AMOUNT = "UPDATE bets SET bet_amount = ? WHERE id_bet = ?";
    private static final String UPDATE_WIN_COEFFICIENT = "UPDATE bets SET win_coefficient = ? WHERE id_bet = ?";
    private static final String FIND_BET = "SELECT id_bet, login, unique_event_id, bet_amount, bet_status, bet_chosen_team, win_coefficient FROM bets WHERE id_bet = ?";

    public BetDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public boolean insert(Bet bet) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW_BET)) {
            statement.setString(1, bet.getUserLogin());
            statement.setString(2, bet.getSportEventId());
            statement.setString(3, String.valueOf(bet.getBetAmount()));
            statement.setString(4, bet.getBetStatus().toString());
            statement.setString(5, bet.getWinCoefficient().toString());
            statement.setString(6, bet.getChosenTeam());
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            LOGGER.error("Error while inserting new bet: " + e);
            throw new DaoException("Can't insert new user to Database: ", e);
        }
        return match;
    }

    @Override
    public boolean delete(Bet bet) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BET)) {
            statement.setString(1, String.valueOf(bet.getId()));
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            LOGGER.error("Can't delete bet in Database: " + e);
            throw new DaoException("Can't delete bet in Database. " + e);
        }
        return match;
    }

    @Override
    public List<Bet> findAll() throws DaoException {
        List<Bet> bets;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BETS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            BetMapper betMapper = BetMapper.getInstance();
            bets = betMapper.retrieve(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Error has occurred while finding bets: " + e);
            throw new DaoException("Error has occurred while finding bets: ", e);
        }
        return bets;
    }

    @Override
    public List<Bet> findAllUserBetsByLogin(String userLogin) throws DaoException {
        List<Bet> bets;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BETS_BY_USER_LOGIN)) {
            preparedStatement.setString(1, userLogin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                BetMapper betMapper = BetMapper.getInstance();
                bets = betMapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.info("Exception while finding all user bets: " + e);
            throw new DaoException("Exception while finding all user bets: ", e);
        }
        return bets;
    }

    @Override
    public List<Bet> findAllBetsByEventId(int eventId) throws DaoException {
        List<Bet> bets;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BETS_BY_EVENT_ID)) {
            preparedStatement.setString(1, String.valueOf(eventId));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                BetMapper betMapper = BetMapper.getInstance();
                bets = betMapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.info("Exception while finding all event bets: " + e);
            throw new DaoException("Exception while finding all event bets: ", e);
        }
        return bets;
    }

    @Override
    public List<Bet> findProcessingBetsForProvidedTeamByEventId(int eventId, String providedTeam) throws DaoException {
        List<Bet> bets;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BETS_FOR_PROVIDED_TEAM_BY_EVENT_ID)) {
            preparedStatement.setString(1, String.valueOf(eventId));
            preparedStatement.setString(2, providedTeam);
            preparedStatement.setString(3, PROCESSING.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                BetMapper betMapper = BetMapper.getInstance();
                bets = betMapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.info("Exception while finding all event team bets: " + e);
            throw new DaoException("Exception while finding all event team bets: ", e);
        }
        return bets;
    }

    @Override
    public boolean updateBetStatus(List<Bet> bets) throws DaoException {
        boolean status;
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BETS_STATUS)) {
            for (Bet bet : bets) {
                preparedStatement.setString(1, bet.getBetStatus().toString());
                preparedStatement.setString(2, bet.getUserLogin());
                preparedStatement.setString(3, String.valueOf(bet.getId()));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            status = true;
        } catch (SQLException e) {
            LOGGER.info("Exception while updating bet status: " + e);
            throw new DaoException("Exception while updating bet status: ", e);
        }
        return status;
    }

    @Override
    public List<Bet> findAllUserBetsByBetStatus(String login, BetStatus status) throws DaoException {
        List<Bet> bets;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BETS_BY_BET_STATUS)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, status.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                BetMapper betMapper = BetMapper.getInstance();
                bets = betMapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.info("Exception while searching all user bets by bet status: " + e);
            throw new DaoException("Exception while searching all user bets by bet status: ", e);
        }
        return bets;
    }

    @Override
    public boolean updateBetAmount(String betId, BigDecimal betAmount) throws DaoException {
        boolean status;
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BET_AMOUNT)) {
            preparedStatement.setString(1, betId);
            preparedStatement.setString(2, betAmount.toString());
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            LOGGER.info("Exception while updating bet amount: " + e);
            throw new DaoException("Exception while updating bet amount: ", e);
        }
        return status;
    }

    @Override
    public boolean updateBetStatus(String betId, BetStatus betStatus) throws DaoException {
        boolean status;
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BET_STATUS)) {
            preparedStatement.setString(1, betId);
            preparedStatement.setString(2, betStatus.toString());
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            LOGGER.info("Exception while updating bet status: " + e);
            throw new DaoException("Exception while updating bet status: ", e);
        }
        return status;
    }

    @Override
    public boolean updateWinCoefficient(String betId, BigDecimal coefficient) throws DaoException {
        boolean status;
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_WIN_COEFFICIENT)) {
            preparedStatement.setString(1, betId);
            preparedStatement.setString(2, coefficient.toString());
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            LOGGER.info("Exception while updating bet coefficient: " + e);
            throw new DaoException("Exception while updating bet coefficient: ", e);
        }
        return status;
    }

    @Override
    public Optional<Bet> findBet(String betId) throws DaoException {
        List<Bet> bets;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BET)) {
            preparedStatement.setString(1, betId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                BetMapper betMapper = BetMapper.getInstance();
                bets = betMapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Error has occurred while finding bet: " + e);
            throw new DaoException("Error has occurred while finding bet: ", e);
        }
        return bets.isEmpty() ? Optional.empty() : Optional.of(bets.get(0));
    }
}
