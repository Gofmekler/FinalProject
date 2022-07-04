package by.maiseichyk.finalproject.pool;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;

public class ConnectionPoolTest {
    private ConnectionPool connectionPool;

    @BeforeClass
    public void init() {
        connectionPool = ConnectionPool.getInstance();
    }

    @Test
    public void getConnection() {
        Connection connection = connectionPool.getConnection();
        Assert.assertNotNull(connection);
    }

    @Test
    public void releaseConnection() {
        Connection connection = connectionPool.getConnection();
        boolean actual = connectionPool.releaseConnection(connection);
        Assert.assertTrue(actual);
    }
}