package by.maiseichyk.finalproject.dao.mapper;

import by.maiseichyk.finalproject.entity.AbstractEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/**
 * @project Totalizator
 * @author Maiseichyk
 * The interface Mapper.
 *
 * @param <T> the type parameter
 */
public interface Mapper<T extends AbstractEntity> {
    /**
     * @param resultSet the result set
     * @return the list
     * @throws SQLException the sql exception
     */
    List<T> retrieve(ResultSet resultSet) throws SQLException;
}
