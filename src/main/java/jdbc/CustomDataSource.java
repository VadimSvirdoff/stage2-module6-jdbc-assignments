package jdbc;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import java.util.Properties;

@Getter
@Setter
public class CustomDataSource implements DataSource {
    private static volatile CustomDataSource instance;
    private final String driver;
    private final String url;
    private final String name;
    private final String password;

    private CustomDataSource(String driver, String url, String password, String name) {
        this.driver = driver;
        this.url = url;
        this.name = name;
        this.password = password;
    }

    public static CustomDataSource getInstance() {
        if (instance == null) {
            synchronized (CustomDataSource.class) {
                if (instance == null) {
                    Properties properties = new Properties();
                    try (FileInputStream fileInputStream = new FileInputStream("app.properties")) {
                        properties.load(fileInputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String driver = properties.getProperty("postgres.driver");
                    String url = properties.getProperty("postgres.url");
                    String username = properties.getProperty("postgres.username");
                    String password = properties.getProperty("postgres.password");

                    instance = new CustomDataSource(driver, url, password, username);
                }
            }
        }
        return instance;
    }


    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, name, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new SQLException();
    }

    @Override
    public void setLogWriter(PrintWriter printWriter) throws SQLException {
        throw new SQLException();

    }

    @Override
    public void setLoginTimeout(int i) throws SQLException {
        throw new SQLException();

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new SQLException();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        throw new SQLException();
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        throw new SQLException();
    }
}
