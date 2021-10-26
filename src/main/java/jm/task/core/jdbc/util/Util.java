package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL =  "jdbc:mysql://localhost:3306/test_db?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static SessionFactory factory;

    public static SessionFactory getSessionFactory() {
        if(factory == null) {
            try {
                Properties props = new Properties();
                props.setProperty("hibernate.connection.url", URL);
                props.setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect");
                props.setProperty("hibernate.connection.username", USER);
                props.setProperty("hibernate.connection.password", PASSWORD);
                props.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                props.setProperty("show_sql", "true");
                props.setProperty("hibernate.current_session_context_class", "thread");
                props.setProperty("hibernate.hbm2ddl.auto", "update");
                Configuration configuration = new Configuration();
                configuration.setProperties(props);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                factory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception ex) {
                System.out.println("===== CREATE SESSION ERROR =====\n" + ex);
            }
        }
        return factory;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("Connection error\n" + ex);
        }
        return connection;
    }
}
