package flashtanki.server.battles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import flashtanki.server.logger.Logger;

public class BattleProcessor {
	public static List<BattleModel> battles;
	
	public static void init() {
		Logger.log(Logger.INFO, "Init BattleProccessor...");
		battles = new ArrayList<BattleModel>();
	}
	
	public static void addBattle(BattleModel battle) {
		battles.add(battle);
	}
	
	public static String generateId() {
	    Random random = new Random();
	    long randomValue = random.nextLong() & Long.MAX_VALUE;
	    String hexString = Long.toHexString(randomValue);
	    return String.format("%1$" + 16 + "s", hexString).replace(' ', '0');
	}

	public static BattleModel getBattleById(String battleId) {
		for (BattleModel battle : battles) {
			if (battle.battleId.contains(battleId)) {
				return battle;
			}
		}
		return null;
	}
}
