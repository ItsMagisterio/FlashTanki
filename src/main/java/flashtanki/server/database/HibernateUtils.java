package flashtanki.server.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import flashtanki.server.logger.Logger;

public class HibernateUtils {
    private static SessionFactory sessionFactory;

    public static void setupSessionFactory() {
        Configuration config = getConfiguration();
        Logger.log(Logger.INFO, "Configured: " + config.getProperty("hibernate.connection.username") + ":" + config.getProperty("hibernate.connection.url"));
        sessionFactory = config.buildSessionFactory();
    }

    private static Configuration getConfiguration() {
        Configuration config = new Configuration();

        config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/flashtanki");
        config.setProperty("hibernate.connection.username", "root");
        config.setProperty("hibernate.connection.password", "");
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        config.setProperty("hibernate.show_sql", "false");
        config.setProperty("hibernate.format_sql", "true");
        config.setProperty("hibernate.hbm2ddl.auto", "update");

        return config;
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}
