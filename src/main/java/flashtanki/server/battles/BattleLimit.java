package flashtanki.server.battles;

import org.json.simple.JSONObject;

public class BattleLimit extends JSONObject {
   public BattleLimit(String battleMode, int scoreLimit, int timeLimitInSec) {
	   this.put("battleMode", battleMode);
	   this.put("scoreLimit", scoreLimit);
	   this.put("timeLimitInSec", timeLimitInSec);
   }
}
