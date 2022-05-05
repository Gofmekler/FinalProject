package by.maiseichyk.finalproject.mapper;

import by.maiseichyk.finalproject.entity.AbstractEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Mapper<T extends AbstractEntity> {
    List<T> retrieve(ResultSet resultSet) throws SQLException;
}
