package by.maiseichyk.finalproject.dao;

import by.maiseichyk.finalproject.entity.AbstractEntity;
import by.maiseichyk.finalproject.exception.DaoException;

import java.util.List;

public abstract class BaseDao<T extends AbstractEntity> {
    public abstract boolean insert(T t) throws DaoException;
    public abstract boolean delete(T t) throws DaoException;
    public abstract List<T> findAll() throws DaoException;
    public abstract T update(T t) throws DaoException;


}