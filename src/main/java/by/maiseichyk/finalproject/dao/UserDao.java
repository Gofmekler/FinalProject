package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;

import java.util.Optional;

public interface UserDao {
    Optional<User> authenticate(String login, String password) throws DaoException;
}
