package by.maiseichyk.finalproject.dao.mapper.impl;

import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.entity.UserRole;
import by.maiseichyk.finalproject.dao.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class UserMapper implements Mapper<User> {
    private static UserMapper instance = new UserMapper();

    private UserMapper() {
    }

    public static UserMapper getInstance() {
        return instance;
    }

    @Override
    public List<User> retrieve(ResultSet resultSet) throws SQLException {
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User.UserBuilder()
                    .setId(resultSet.getInt(USER_ID))
                    .setLogin(resultSet.getString(USER_LOGIN))
                    .setPassword(resultSet.getString(USER_PASSWORD))
                    .setName(resultSet.getString(USER_NAME))
                    .setLastname(resultSet.getString(USER_LASTNAME))
                    .setEmail(resultSet.getString(USER_EMAIL))
                    .setUserRole(UserRole.valueOf(resultSet.getString(USER_ROLE)))
                    .setBalance(resultSet.getBigDecimal(USER_BALANCE))
                    .build();
            userList.add(user);
        }
        return userList;
    }
}
