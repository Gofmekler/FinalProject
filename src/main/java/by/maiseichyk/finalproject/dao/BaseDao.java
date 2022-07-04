package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.AbstractEntity;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.pool.ConnectionPool;

import java.sql.Connection;
import java.util.List;
/**
 * @project Totalizator
 * @author Maiseichyk
 * The type base dao.
 *
 * @param <T> the type parameter
 */
public abstract class BaseDao<T extends AbstractEntity> {
    protected Connection connection;

    /**
     * @param t the t
     * @return boolean
     * @throws DaoException the dao exception
     */
    public abstract boolean insert(T t) throws DaoException;

    /**
     * @param t the t
     * @return boolean
     * @throws DaoException the dao exception
     */
    public abstract boolean delete(T t) throws DaoException;

    /**
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<T> findAll() throws DaoException;

    /**
     * @param connection the connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * closes the connection
     */
    public void closeConnection() {
        if (connection != null) {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }
}
