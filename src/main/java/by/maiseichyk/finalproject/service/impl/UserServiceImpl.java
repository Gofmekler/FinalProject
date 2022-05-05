package by.maiseichyk.finalproject.service.impl;

import by.maiseichyk.finalproject.dao.impl.UserDaoImpl;
import by.maiseichyk.finalproject.entity.User;
import by.maiseichyk.finalproject.exception.DaoException;
import by.maiseichyk.finalproject.exception.ServiceException;
import by.maiseichyk.finalproject.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<User> findUser(String login, String password) throws ServiceException {//validate pass login
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            Optional<User> user = userDao.authenticate(login, password);
            if(user.isPresent()){
                return user;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return Optional.empty();
    }
}
