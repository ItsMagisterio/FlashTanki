package flashtanki.server.user;

import flashtanki.server.protocol.commands.Commands;
import flashtanki.server.utils.Rank;
import flashtanki.server.utils.RankUtils;

public class User {
    public String username;
    public String password;
    public int score;
    public int crystals;
    public Rank rank;
    
    public User(String username, String password, int score, int crystals) {
    	this.username = username;
    	this.password = password;
    	this.score = score;
    	this.crystals = crystals;
    	this.rank = RankUtils.getRankByScore(this.score);
    }
    
	public int getNextScore() {
		return this.rank.max + 1;
	}
}
