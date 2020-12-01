package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory = null;
    private final String hostName = "localhost";
    private final String dbName = "taskjdbc";
    private final String userName = "root";
    private final String password = "12345";
    private final String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(connectionURL, userName, password);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public  SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/taskjdbc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "12345");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put( Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.USE_REFLECTION_OPTIMIZER, "false");
           //settings.put(Environment.HBM2DDL_AUTO, "false");
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration
                            .getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
    public void shutDown(){
        getSessionFactory().close();
    }
}
