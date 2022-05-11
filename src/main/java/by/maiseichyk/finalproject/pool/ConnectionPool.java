package by.maiseichyk.finalproject.pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final int POOL_SIZE = 8;
    private static ConnectionPool instance = new ConnectionPool();
    private BlockingQueue<Connection> freeConnections = new LinkedBlockingQueue<>(POOL_SIZE);//const + add new blocking queue
    private BlockingQueue<Connection> usedConnections = new LinkedBlockingQueue<>(POOL_SIZE);//new queue for checking
    private static ReentrantLock reentrantLock = new ReentrantLock();

    {//FIXME non-static
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e.getMessage());//log todo catch to new error page
        }
    }

    private ConnectionPool() {
        String url = "jdbc:mysql://localhost:3306/datatest";
        Properties prop = new Properties();
        prop.put("user", "root"); //todo вынести в файл
        prop.put("password", "1352295");
        for (int i = 0; i < POOL_SIZE; i++) {//const 8
            Connection connection;
            try {
                connection = DriverManager.getConnection(url, prop);
            } catch (SQLException e) {
                throw new ExceptionInInitializerError(e.getMessage());//todo log может не дать соединение
            }
            freeConnections.add(connection);
        }
    }

    public static ConnectionPool getInstance() {
        try {
            reentrantLock.lock();
            if (instance == null) {
                instance = new ConnectionPool();
            }
        } finally {
            reentrantLock.unlock();
        }
        return instance;//todo double check multithreading
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            usedConnections.put(connection);
        } catch (InterruptedException e) {//todo log warn threadDeath
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        //todo check connection
        try {
            usedConnections.remove(connection);
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();//todo log
        }
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.take().close();
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();//todo log
            }
        }
    }

    public void deregisterDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
//                log
            } catch (SQLException e) {
                //log
            }
        }

    }
}
