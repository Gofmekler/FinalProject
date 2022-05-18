package by.maiseichyk.finalproject.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final String POOL_PROPERTY_FILE = "database";
    private static final String DATABASE_URL_PROPERTY = "url";
    private static final String DATABASE_USER_PROPERTY = "user";
    private static final String DATABASE_PASSWORD_PROPERTY = "password";
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final int DEFAULT_POOL_SIZE = 8;
    private static ConnectionPool instance = new ConnectionPool();
    private BlockingQueue<ProxyConnection> freeConnections = new LinkedBlockingQueue<>(DEFAULT_POOL_SIZE);//const + add new blocking queue
    private BlockingQueue<ProxyConnection> usedConnections = new LinkedBlockingQueue<>(DEFAULT_POOL_SIZE);//new queue for checking
    private static ReentrantLock reentrantLock = new ReentrantLock();

    {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e.getMessage());//log todo catch to new error page
        }
    }

    private ConnectionPool() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(POOL_PROPERTY_FILE);
        String url = resourceBundle.getString(DATABASE_URL_PROPERTY);
        String user = resourceBundle.getString(DATABASE_USER_PROPERTY);
        String password = resourceBundle.getString(DATABASE_PASSWORD_PROPERTY);
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.add(proxyConnection);
            } catch (SQLException e) {
                LOGGER.error("Error has occurred while creating connection: " + e);
//                throw new ExceptionInInitializerError(e.getMessage());//todo log может не дать соединение
            }
        }
        if (freeConnections.isEmpty()) {
            LOGGER.fatal("Error: no connections were created");
            throw new RuntimeException("Error: no connections were created");
        }
        LOGGER.info("{} connections were created", freeConnections.size());
    }

    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            try {
                reentrantLock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
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
            freeConnections.put((ProxyConnection) connection);
        } catch (InterruptedException e) {
            e.printStackTrace();//todo log
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
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
