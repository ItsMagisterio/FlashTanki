package flashtanki.server.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	
	private static SessionFactory builder;
	
	public static void init() {
		Configuration config = getConfiguration();
		builder = config.buildSessionFactory();
	}
	
	private static Configuration getConfiguration() {
        Configuration config = new Configuration();

        config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");

        config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/flashtankionline");
        config.setProperty("hibernate.connection.username", "root");
        config.setProperty("hibernate.connection.password", "");
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        config.setProperty("hibernate.show_sql", "false");
        config.setProperty("hibernate.format_sql", "true");
        config.setProperty("hibernate.hbm2ddl.auto", "update");
        return config;
    }
	
	public static Session getSession() {
		return builder.openSession();
	}
}
