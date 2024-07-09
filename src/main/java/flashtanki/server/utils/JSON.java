package flashtanki.server.utils;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import flashtanki.server.battles.BattleController;
import flashtanki.server.battles.BattleLimit;
import flashtanki.server.battles.BattleMode;
import flashtanki.server.battles.BattleModel;
import flashtanki.server.battles.BattleProperty;
import flashtanki.server.battles.Map;

public class JSON {
	public static String parseInitRegistrationModelData() {
		JSONObject obj = new JSONObject();
		obj.put("bgResource", 122842);
		obj.put("enableRequiredEmail", false);
		obj.put("maxPasswordLength", 100);
		obj.put("minPasswordLength", 6);
		return obj.toJSONString();
	}

	public static String parseInitPanelData(String name, int crystall, String email, boolean tester, int next_score, int place, int rang, int rating, int score, int currentRankScore, boolean hasDoubleCrystal, int durationCrystalAbonement, String userProfileUrl) {
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		obj.put("crystall", crystall);
		obj.put("email", email);
		obj.put("tester", tester);
		obj.put("next_score", next_score);
		obj.put("place", place);
		obj.put("rang", rang);
		obj.put("rating", rating);
		obj.put("score", score);
		obj.put("currentRankScore", currentRankScore);
		obj.put("hasDoubleCrystal", hasDoubleCrystal);
		obj.put("durationCrystalAbonement", durationCrystalAbonement);
		obj.put("userProfileUrl", userProfileUrl);
		return obj.toJSONString();
	}

	public static String parseInitBattleSelectData(List battles) {
		JSONObject obj = new JSONObject();
		obj.put("battles", battles);
		return obj.toJSONString();
	}

	public static String parseInitBattleCreateData(List<BattleLimit> battleLimits, List<Map> maps) {
		JSONObject obj = new JSONObject();
		obj.put("battleLimits", battleLimits);
		obj.put("maps", maps);
		return obj.toJSONString();
	}

	public static JSONObject parse(String pars) {
		JSONParser jsonParser = new JSONParser();
		try {
			return (JSONObject) jsonParser.parse(pars);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String parseBattleData(BattleModel battle) {
		JSONObject obj = new JSONObject();
		obj.put("battleId", battle.battleId);
		obj.put("battleMode", battle.battleMode.name);
		obj.put("name", battle.name);
		obj.put("proBattle", battle.battleProperties.get(BattleProperty.ProBattle));
		obj.put("privateBattle", battle.battleProperties.get(BattleProperty.privateBattle));
		obj.put("maxPeople", battle.battleProperties.get(BattleProperty.MaxPeople));
		obj.put("minRank", battle.battleProperties.get(BattleProperty.MinRank));
		obj.put("maxRank", battle.battleProperties.get(BattleProperty.MaxRank));
		obj.put("preview", new Map().get("preview"));
		obj.put("parkourMode", battle.battleProperties.get(BattleProperty.ParkourMode));
		obj.put("timeLeft", 900);
		obj.put("users", new ArrayList());
		return obj.toJSONString();
	}

	public static String parseBattleInfo(BattleModel battle) {
		JSONObject obj = new JSONObject();
		obj.put("itemId", battle.battleId);
		obj.put("battleMode", battle.battleMode.name);
		obj.put("name", battle.name);
		obj.put("timeLimitInSec", battle.battleProperties.get(BattleProperty.TimeLimit));
		obj.put("scoreLimit", battle.battleProperties.get(BattleProperty.ScoreLimit));
		obj.put("proBattle", battle.battleProperties.get(BattleProperty.ProBattle));
		obj.put("maxPeopleCount", battle.battleProperties.get(BattleProperty.MaxPeople));
		obj.put("spectator", false);
		obj.put("withoutBonuses", battle.battleProperties.get(BattleProperty.WithoutBonuses));
		obj.put("withoutCrystals", battle.battleProperties.get(BattleProperty.WithoutCrystals));
		obj.put("withoutSupplies", battle.battleProperties.get(BattleProperty.WithoutSupplies));
		obj.put("reArmorEnabled", battle.battleProperties.get(BattleProperty.RearmingEnabled));
		obj.put("minRank", battle.battleProperties.get(BattleProperty.MinRank));
		obj.put("maxRank", battle.battleProperties.get(BattleProperty.MaxRank));
		obj.put("preview", new Map().get("preview"));
		obj.put("parkourMode", battle.battleProperties.get(BattleProperty.ParkourMode));
		obj.put("timeLeftInSec", 900);
		obj.put("users", new ArrayList());
		return obj.toJSONString();
	}

	public static String parseInitBattleModelData(BattleModel battle) {
		JSONObject obj = new JSONObject();
		obj.put("battleId", battle.battleId);
		obj.put("map_id", "map_sandbox");
		obj.put("mapId", 336728);
		obj.put("spectator", false);
		obj.put("reArmorEnabled", battle.battleProperties.get(BattleProperty.RearmingEnabled));
		obj.put("minRank", 1);
		obj.put("maxRank", 30);
		obj.put("sound_id", 584396);
		obj.put("kick_period_ms", 125000);
		obj.put("invisible_time", 3500);
		obj.put("active", true);
		obj.put("dustParticle", 110001);
		obj.put("skybox", "{\"top\":45572,\"front\":57735,\"back\":268412,\"bottom\":31494,\"left\":927961,\"right\":987391}");
		obj.put("map_graphic_data", "{\"angleX\":-0.8500000238418579,\"angleZ\":2.5,\"lightColor\":13090219,\"shadowColor\":5530735,\"fogAlpha\":0.25,\"fogColor\":10543615,\"farLimit\":10000,\"nearLimit\":5000,\"gravity\":1000,\"skyboxRevolutionSpeed\":0.0,\"ssaoColor\":2045258,\"dustAlpha\":0.75,\"dustDensity\":0.15000000596046448,\"dustFarDistance\":7000,\"dustNearDistance\":5000,\"dustParticle\":\"summer\",\"dustSize\":200}");
		return obj.toJSONString();
	}

	public static String parseInitGuiModelData(BattleModel battle) {
		JSONObject obj = new JSONObject();
		List<JSONObject> users = new ArrayList<>();
		for (BattleController player : battle.users.values()) {
			users.add(JSON.parseBattleUser(player));
		}
		obj.put("name", battle.name);
		obj.put("fund", 1337);
		obj.put("scoreLimit", battle.battleProperties.get(BattleProperty.ScoreLimit));
		obj.put("timeLimit", battle.battleProperties.get(BattleProperty.TimeLimit));
		obj.put("currTime", 900);
		obj.put("team", battle.battleMode != BattleMode.Deathmatch || battle.battleMode != BattleMode.Juggernaut);
		obj.put("parkourMode", battle.battleProperties.get(BattleProperty.ParkourMode));
		obj.put("battleType", battle.battleMode.name);
		obj.put("equipmentConstraintsMode", "NONE");
		obj.put("score_blue", 0);
		obj.put("score_red", 0);
		obj.put("users", users);
		return obj.toJSONString();
	}

	public static String parseShowSettingsData() {
		JSONObject obj = new JSONObject();
		obj.put("emailNotice", false);
		obj.put("email", null);
		obj.put("notificationEnabled", true);
		obj.put("showDamageEnabled", true);
		obj.put("isConfirmEmail", false);
		obj.put("authorizationUrl", "http://localhost/");
		obj.put("linkExists", false);
		obj.put("snId", "vkontakte");
		obj.put("passwordCreated", true);
		return obj.toJSONString();
	}

	public static String parseInitDMStatistics(List<JSONObject> stats) {
		JSONObject obj = new JSONObject();
		obj.put("users", stats);
		return obj.toJSONString();
	}

	public static JSONObject parseUserStat(BattleController controller) {
		JSONObject obj = new JSONObject();
		obj.put("chatModeratorLevel", 4);
		obj.put("deaths", controller.stat.deaths);
		obj.put("kills", controller.stat.kills);
		obj.put("rank", RankUtils.getNumberRank(controller.client.user.rank));
		obj.put("score", controller.stat.score);
		obj.put("uid", controller.client.user.username);
		return obj;
	}

	public static JSONObject parseBattleUser(BattleController controller) {
		JSONObject obj = new JSONObject();
		obj.put("rank", RankUtils.getNumberRank(controller.client.user.rank));
		obj.put("teamType", "NONE");
		obj.put("nickname", controller.client.user.username);
		return obj;
	}

	public static String parseInitPremiumData(int leftTime, boolean needShowNotificationCompletionPremium, boolean needShowWelcomeAlert, int reminderCompletionPremiumTime, boolean wasShowAlertForFirstPurchasePremium, boolean wasShowReminderCompletionPremium) {
		JSONObject obj = new JSONObject();
		obj.put("left_time", leftTime);
		obj.put("need_show_notification_completion_premium", needShowNotificationCompletionPremium);
		obj.put("need_show_welcome_alert", needShowWelcomeAlert);
		obj.put("reminder_completion_premium_time", reminderCompletionPremiumTime);
		obj.put("was_show_alert_for_first_purchase_premium", wasShowAlertForFirstPurchasePremium);
		obj.put("was_show_reminder_completion_premium", wasShowReminderCompletionPremium);
		return obj.toJSONString();
	}

	public static String parseInitTankData(
			String battleId,
			String hullId,
			String turretId,
			String colormapId,
			String hullResource,
			String turretResource,
			String partsObject,
			String tankId,
			String nickname,
			String teamType,
			String state,
			int health,
			double maxSpeed,
			double maxTurnSpeed,
			double acceleration,
			double reverseAcceleration,
			double sideAcceleration,
			double turnAcceleration,
			double reverseTurnAcceleration,
			double dampingCoeff,
			double mass,
			double power,
			double turretTurnSpeed,
			double turretTurnAcceleration,
			double kickback,
			double impactForce,
			String sfxData
	) {
		JSONObject obj = new JSONObject();
		obj.put("battleId", battleId);
		obj.put("hull_id", hullId);
		obj.put("turret_id", turretId);
		obj.put("colormap_id", colormapId);
		obj.put("hullResource", hullResource);
		obj.put("turretResource", turretResource);
		obj.put("partsObject", partsObject);
		obj.put("tank_id", tankId);
		obj.put("nickname", nickname);
		obj.put("team_type", teamType);
		obj.put("state", state);
		obj.put("health", health);

		// Hull physics
		obj.put("maxSpeed", maxSpeed);
		obj.put("maxTurnSpeed", maxTurnSpeed);
		obj.put("acceleration", acceleration);
		obj.put("reverseAcceleration", reverseAcceleration);
		obj.put("sideAcceleration", sideAcceleration);
		obj.put("turnAcceleration", turnAcceleration);
		obj.put("reverseTurnAcceleration", reverseTurnAcceleration);
		obj.put("dampingCoeff", dampingCoeff);
		obj.put("mass", mass);
		obj.put("power", power);

		// Weapon physics
		obj.put("turret_turn_speed", turretTurnSpeed);
		obj.put("turretTurnAcceleration", turretTurnAcceleration);
		obj.put("kickback", kickback);
		obj.put("impact_force", impactForce);

		// Weapon visual
		obj.put("sfxData", sfxData);

		return obj.toJSONString();
	}
}
