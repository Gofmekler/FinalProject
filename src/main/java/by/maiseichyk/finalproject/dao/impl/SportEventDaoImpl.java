package by.maiseichyk.finalproject.dao.impl;

import by.maiseichyk.finalproject.dao.BaseDao;
import by.maiseichyk.finalproject.dao.SportEventDao;
import by.maiseichyk.finalproject.entity.SportEvent;
import by.maiseichyk.finalproject.entity.SportEventType;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.dao.mapper.impl.EventMapper;
import by.maiseichyk.finalproject.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SportEventDaoImpl extends BaseDao<SportEvent> implements SportEventDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ID_EVENT_SELECT = "SELECT unique_event_id, event_type, first_team, first_team_ratio, second_team, second_team_ratio, event_date, event_result FROM testevent WHERE unique_event_id = ?";
    private static final String SELECT_EVENT_BY_TEAMS_NAME = "SELECT unique_event_id, event_type, first_team, first_team_ratio, second_team, second_team_ratio, event_date, event_result FROM testevent WHERE first_team = ? AND second_team = ?";
    private static final String DELETE_EVENT = "DELETE FROM testevent WHERE unique_event_id = ?";
    private static final String INSERT_EVENT = "INSERT INTO testevent(event_type, first_team, first_team_ratio, second_team, second_team_ratio, event_date) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_EVENT_TYPE = "UPDATE testevent SET event_type = ? WHERE unique_event_id = ?";
    private static final String UPDATE_EVENT_FIRST_TEAM_NAME = "UPDATE testevent SET first_team = ? WHERE unique_event_id = ?";
    private static final String UPDATE_EVENT_FIRST_TEAM_RATIO = "UPDATE testevent SET first_team_ratio = ? WHERE unique_event_id = ?";
    private static final String UPDATE_EVENT_SECOND_TEAM_NAME = "UPDATE testevent SET second_team = ? WHERE unique_event_id = ?";
    private static final String UPDATE_EVENT_SECOND_TEAM_RATIO = "UPDATE testevent SET second_team_ratio = ? WHERE unique_event_id = ?";
    private static final String UPDATE_EVENT_DATE = "UPDATE testevent SET event_date = ? WHERE unique_event_id = ?";
    private static final String SELECT_ALL_EVENTS = "SELECT unique_event_id, event_type, first_team, first_team_ratio, second_team, second_team_ratio, event_date, event_result FROM testevent";
    private static final String SELECT_ALL_PAST_EVENTS = "SELECT unique_event_id, event_type, first_team, first_team_ratio, second_team, second_team_ratio, event_date, event_result FROM past_events";
    private static final String INSERT_PAST_EVENT = "INSERT INTO past_events(unique_event_id, event_type, first_team, first_team_ratio, second_team, second_team_ratio, event_date, event_result) VALUES (?,?,?,?,?,?,?,?)";

    public SportEventDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public boolean insert(SportEvent sportEvent) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_EVENT)) {
            statement.setString(1, sportEvent.getEventType().toString());
            statement.setString(2, sportEvent.getFirstTeam());
            statement.setString(3, String.valueOf(sportEvent.getFirstTeamRatio()));
            statement.setString(4, sportEvent.getSecondTeam());
            statement.setString(5, String.valueOf(sportEvent.getSecondTeamRatio()));
            statement.setString(6, sportEvent.getEventDate().toString());
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            LOGGER.info("Can't insert new event to Database. " + e);
            throw new DaoException("Can't insert new event to Database. " + e);
        }
        return match;
    }

    @Override
    public boolean delete(SportEvent sportEvent) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_EVENT)) {
            statement.setString(1, String.valueOf(sportEvent.getId()));
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            LOGGER.info("Can't delete event to Database. " + e);
            throw new DaoException("Can't delete event in Database. " + e);
        }
        return match;
    }

    @Override
    public List<SportEvent> findAll() throws DaoException {
        List<SportEvent> sportEventList;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EVENTS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            EventMapper eventMapper = EventMapper.getInstance();
            sportEventList = eventMapper.retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding events: " + exception);
            throw new DaoException("Error has occurred while finding events: ", exception);
        }
        return sportEventList;
    }

    @Override
    public Optional<SportEvent> findSportEventInDb(String eventId) throws DaoException {
        List<SportEvent> sportEventList;
        try (PreparedStatement preparedStatement = connection.prepareStatement(ID_EVENT_SELECT)) {
            preparedStatement.setString(1, eventId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                EventMapper eventMapper = EventMapper.getInstance();
                sportEventList = eventMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding event by id: " + exception.getMessage());
            throw new DaoException("Error has occurred while finding event by id: ", exception);
        }
        return sportEventList.isEmpty() ? Optional.empty() : Optional.of(sportEventList.get(0));
    }

    @Override
    public List<SportEvent> findSportEventByTeamsName(String firstTeamName, String secondTeamName) throws DaoException {
        List<SportEvent> sportEventList;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENT_BY_TEAMS_NAME)) {
            preparedStatement.setString(1, firstTeamName);
            preparedStatement.setString(2, secondTeamName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                EventMapper eventMapper = EventMapper.getInstance();
                sportEventList = eventMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding event by teams name: " + exception.getMessage());
            throw new DaoException("Error has occurred while finding event by teams name: ", exception);
        }
        return sportEventList;
    }

    @Override
    public boolean insertPastSportEvent(SportEvent sportEvent, String result) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PAST_EVENT)) {
            statement.setString(1, String.valueOf(sportEvent.getId()));
            statement.setString(2, sportEvent.getEventType().toString());
            statement.setString(3, sportEvent.getFirstTeam());
            statement.setString(4, String.valueOf(sportEvent.getFirstTeamRatio()));
            statement.setString(5, sportEvent.getSecondTeam());
            statement.setString(6, String.valueOf(sportEvent.getSecondTeamRatio()));
            statement.setString(7, sportEvent.getEventDate().toString());
            statement.setString(8, result);
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            LOGGER.info("Can't insert passed event to Database. " + e);
            throw new DaoException("Can't insert passed event to Database. " + e);
        }
        return match;
    }

    @Override
    public List<SportEvent> findAllPastEvents() throws DaoException {
        List<SportEvent> sportEventList;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PAST_EVENTS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            EventMapper eventMapper = EventMapper.getInstance();
            sportEventList = eventMapper.retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding past events: " + exception);
            throw new DaoException("Error has occurred while finding past events: ", exception);
        }
        return sportEventList;
    }

    @Override
    public boolean updateFirstTeamName(String eventId, String changeTo) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EVENT_FIRST_TEAM_NAME)) {
            statement.setString(1, changeTo);
            statement.setString(2, eventId);
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            LOGGER.info("Can't update first team info to Database. " + e);
            throw new DaoException("Can't update first team info to Database. " + e);
        }
        return match;
    }

    @Override
    public boolean updateSecondTeamName(String eventId, String changeTo) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EVENT_SECOND_TEAM_NAME)) {
            statement.setString(1, changeTo);
            statement.setString(2, eventId);
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            LOGGER.info("Can't update second team info to Database. " + e);
            throw new DaoException("Can't update second team info to Database. " + e);
        }
        return match;
    }

    @Override
    public boolean updateFirstTeamRatio(String eventId, BigDecimal changeTo) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EVENT_FIRST_TEAM_RATIO)) {
            statement.setString(1, changeTo.toString());
            statement.setString(2, eventId);
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            LOGGER.info("Can't update first team ratio info to Database. " + e);
            throw new DaoException("Can't update first team ratio info to Database. " + e);
        }
        return match;
    }

    @Override
    public boolean updateSecondTeamRatio(String eventId, BigDecimal changeTo) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EVENT_SECOND_TEAM_RATIO)) {
            statement.setString(1, changeTo.toString());
            statement.setString(2, eventId);
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            LOGGER.info("Can't update second team ratio info to Database. " + e);
            throw new DaoException("Can't update second team ratio info to Database. " + e);
        }
        return match;
    }

    @Override
    public boolean updateEventDate(String eventId, LocalDate date) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EVENT_DATE)) {
            statement.setString(1, date.toString());
            statement.setString(2, eventId);
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            LOGGER.info("Can't update event date info to Database. " + e);
            throw new DaoException("Can't update event date info to Database. " + e);
        }
        return match;
    }

    @Override
    public boolean updateEventType(String eventId, SportEventType eventType) throws DaoException {
        boolean match;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EVENT_TYPE)) {
            statement.setString(1, eventType.toString());
            statement.setString(2, eventId);
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            LOGGER.info("Can't update event type info to Database. " + e);
            throw new DaoException("Can't update event type info to Database. " + e);
        }
        return match;
    }
}
