package by.maiseichyk.finalproject.dao.impl;

import by.maiseichyk.finalproject.dao.BaseDao;
import by.maiseichyk.finalproject.dao.SportEventDao;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.mapper.impl.EventMapper;
import by.maiseichyk.finalproject.mapper.impl.UserMapper;
import by.maiseichyk.finalproject.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SportEventDaoImpl extends BaseDao<SportEvent> implements SportEventDao {
    private static final String ID_EVENT_SELECT = "SELECT event_type, first_team, first_team_ratio, second_team, second_team_ratio, event_date FROM testevent WHERE unique_event_id = ?";
    private static final String DELETE_EVENT = "DELETE FROM testevent WHERE unique_event_id = ?";
    private static final String INSERT_EVENT = "INSERT INTO testevent(unique_event_id, event_type, first_team, first_team_ratio, second_team, second_team_ratio, event_date) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE_EVENT = "UPDATE testevent SET event_type = ?, first_team = ?, first_team_ratio = ?, second_team = ?, second_team_ratio = ?, event_date = ? WHERE unique_event_id = ?";
    private static final String SELECT_ALL_EVENTS = "SELECT unique_event_id, event_type, first_team, first_team_ratio, second_team, second_team_ratio, event_date FROM testevent";
    private static final SportEventDaoImpl instance = new SportEventDaoImpl();

    private SportEventDaoImpl() {
    }

    public static SportEventDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(SportEvent sportEvent) throws DaoException {
        boolean match;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_EVENT);
            statement.setString(1, sportEvent.getUniqueEventId());
            statement.setString(2, sportEvent.getEventType().getValue());
            statement.setString(3, sportEvent.getFirstTeam());
            statement.setString(4, sportEvent.getFirstTeamRatio());
            statement.setString(5, sportEvent.getSecondTeam());
            statement.setString(6, sportEvent.getSecondTeamRatio());
            statement.setString(7, sportEvent.getEventDate());
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            throw new DaoException("Can't insert new sportEvent to Database. " + e);
        }
        return match;
    }

    @Override
    public boolean delete(SportEvent sportEvent) throws DaoException {
        boolean match;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_EVENT);
            statement.setString(1, sportEvent.getUniqueEventId());
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            throw new DaoException("Can't delete event in Database. " + e);
        }
        return match;
    }

    @Override
    public List<SportEvent> findAll() throws DaoException {
        List<SportEvent> sportEventList;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EVENTS);
            ResultSet resultSet = preparedStatement.executeQuery();
            EventMapper eventMapper = EventMapper.getInstance();
            sportEventList = eventMapper.retrieve(resultSet);
        } catch (SQLException exception) {
//                LOGGER.error("Error has occurred while finding users: " + exception);
            throw new DaoException("Error has occurred while finding events: ", exception);
        }
        return sportEventList;
    }

    @Override
    public boolean update(SportEvent sportEvent) throws DaoException {
        boolean match;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_EVENT);
            statement.setString(2, sportEvent.getEventType().getValue());
            statement.setString(3, sportEvent.getFirstTeam());
            statement.setString(4, sportEvent.getFirstTeamRatio());
            statement.setString(5, sportEvent.getSecondTeam());
            statement.setString(6, sportEvent.getSecondTeamRatio());
            statement.setString(7, sportEvent.getEventDate());
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            throw new DaoException("Can't update sportEvent info to Database. " + e);
        }
        return match;
    }

    @Override
    public Optional<SportEvent> findSportEventInDb(SportEvent event) throws DaoException {
        List<SportEvent> sportEventList;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ID_EVENT_SELECT);
            preparedStatement.setString(1, event.getUniqueEventId());
            ResultSet resultSet = preparedStatement.executeQuery();
            EventMapper eventMapper = EventMapper.getInstance();
            sportEventList = eventMapper.retrieve(resultSet);
        } catch (SQLException exception) {
//                LOGGER.error("Error has occurred while finding event by id: " + exception);
            throw new DaoException("Error has occurred while finding event by id: ", exception);
        }
        return sportEventList.isEmpty() ? Optional.empty() : Optional.of(sportEventList.get(0));
    }
}
