package flashtanki.server.database;

import flashtanki.server.logger.Logger;
import flashtanki.server.user.User;

public class Repositories {
	public static UserRepository userRepository = new UserRepository();
	
    public static void init() {
    	userRepository.init();
    	userRepository.createUser(new User("TitanoMachina", "test", 100000, 100000));
    	Logger.log(Logger.INFO, "Repositories inited!");
    }
}
