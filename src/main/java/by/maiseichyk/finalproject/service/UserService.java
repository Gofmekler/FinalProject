package by.maiseichyk.finalproject.service;

import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    Optional<User> findUser(String login, String password) throws ServiceException;
}
