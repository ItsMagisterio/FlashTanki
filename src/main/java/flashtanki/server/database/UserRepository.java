package flashtanki.server.database;

import java.util.HashMap;
import flashtanki.server.user.User;

public class UserRepository {
	
	public static HashMap<String, User> users;

	public void init() {
		users = new HashMap<String, User>();
	}
	
	public void createUser(User user) {
		users.put(user.username, user);
	}
	
	public User getUser(String user) {
		return (users.get(user));
	}
	
	public void updateUser(User user) {

	}
}
