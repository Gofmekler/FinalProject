package by.maiseichyk.finalproject.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
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
    private static ConnectionPool instance;
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private final BlockingQueue<ProxyConnection> freeConnections = new LinkedBlockingQueue<>(DEFAULT_POOL_SIZE);
    private final BlockingQueue<ProxyConnection> busyConnections = new LinkedBlockingQueue<>(DEFAULT_POOL_SIZE);


    {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new ExceptionInInitializerError("Driver not registered. " + e);
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
                LOGGER.error("Error has occurred while creating connection: ", e);
            }
        }
        if (freeConnections.isEmpty()) {
            LOGGER.fatal("Error: no connections were created");
            throw new RuntimeException("Error: no connections were created");
        }
        if (freeConnections.size() < DEFAULT_POOL_SIZE) {
            int missingConnectionsNumber = DEFAULT_POOL_SIZE - freeConnections.size();
            for (int j = 0; j < missingConnectionsNumber; j++) {
                try {
                    Connection connection = DriverManager.getConnection(url, user, password);
                    ProxyConnection proxyConnection = new ProxyConnection(connection);
                    freeConnections.add(proxyConnection);
                } catch (SQLException e) {
                    LOGGER.fatal("Connection was not created!", e);
                    throw new ExceptionInInitializerError("Connection was not created!" + e.getMessage());
                }
            }
        }
        LOGGER.info("{} connections were created", freeConnections.size());
    }

    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            try {
                reentrantLock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
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
            busyConnections.put(connection);
        } catch (InterruptedException e) {
            LOGGER.error("Error has occurred while getting connection: ", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        if (!(connection instanceof ProxyConnection)) {
            return false;
        }
        try {
            if (busyConnections.remove(connection)) {
                freeConnections.put((ProxyConnection) connection);
                return true;
            }
        } catch (InterruptedException exception) {
            LOGGER.error("Error has occurred while releasing connection: " + exception);
            Thread.currentThread().interrupt();
        }
        return false;
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
                LOGGER.info("Connection closed.");
            } catch (SQLException | InterruptedException e) {
                LOGGER.error("Error has occurred while destroying connection pool: ", e);
            }
        }
        deregisterDriver();
    }

    private void deregisterDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                LOGGER.info("Driver deregister successfully.");
            } catch (SQLException e) {
                LOGGER.error("Error has occurred while deregistering driver: ", e);
            }
        }
    }
}
