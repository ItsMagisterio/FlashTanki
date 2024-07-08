package flashtanki.server.battles;

import java.util.HashMap;

import flashtanki.server.protocol.commands.Command;
import flashtanki.server.protocol.commands.Commands;

public class BattleModel {
	
	public String name;
	public String battleId;
	public BattleProperties battleProperties;
	public BattleMode battleMode;
	public HashMap<String, BattleController> users = new HashMap<String, BattleController>();
	public HashMap<String, Object> bonuses = new HashMap<String, Object>();
	
	public BattleModel(String name, String battleid, BattleProperties battleproperties, BattleMode mode) {
		this.name = name;
		this.battleId = battleid;
		this.battleProperties = battleproperties;
		this.battleMode = mode;
	}

	public void createTank(BattleController battleController) {
		new Command(Commands.InitTank, "{\"battleId\":\"" + this.battleId + "\",\"hull_id\":\"juggernaut_m0\",\"turret_id\":\"shaft_m2\",\"colormap_id\":966681,\"partsObject\":\"{\\\"engineIdleSound\\\":386284,\\\"engineStartMovingSound\\\":226985,\\\"engineMovingSound\\\":75329,\\\"turretSound\\\":242699}\",\"hullResource\":1500015,\"turretResource\":65798,\"sfxData\":\"{\\\"$type\\\":\\\"shaft\\\",\\\"explosionSound\\\":686923,\\\"explosionTexture\\\":970970,\\\"hitMarkTexture\\\":870536,\\\"muzzleFlashTexture\\\":75337,\\\"shotSound\\\":600555,\\\"targetingSound\\\":938575,\\\"trailTexture\\\":500334,\\\"zoomModeSound\\\":632224,\\\"lighting\\\":[{\\\"name\\\":\\\"shot\\\",\\\"light\\\":[{\\\"attenuationBegin\\\":200,\\\"attenuationEnd\\\":600,\\\"color\\\":15772719,\\\"intensity\\\":0.5,\\\"time\\\":0},{\\\"attenuationBegin\\\":1,\\\"attenuationEnd\\\":2,\\\"color\\\":15772719,\\\"intensity\\\":0.0,\\\"time\\\":600}]},{\\\"name\\\":\\\"hit\\\",\\\"light\\\":[{\\\"attenuationBegin\\\":300,\\\"attenuationEnd\\\":700,\\\"color\\\":15772719,\\\"intensity\\\":0.5,\\\"time\\\":0},{\\\"attenuationBegin\\\":1,\\\"attenuationEnd\\\":2,\\\"color\\\":15772719,\\\"intensity\\\":0.0,\\\"time\\\":600}]}],\\\"bcsh\\\":[{\\\"key\\\":\\\"def\\\",\\\"brightness\\\":0.0,\\\"contrast\\\":0.0,\\\"saturation\\\":0.0,\\\"hue\\\":0.0}],\\\"$type\\\":\\\"shaft\\\"}\",\"position\":\"0.0@0.0@0.0@0.0\",\"incration\":2,\"tank_id\":\"TitanoMachina\",\"nickname\":\"TitanoMachina\",\"team_type\":\"NONE\",\"state\":\"suicide\",\"maxSpeed\":40.0,\"maxTurnSpeed\":2.617994,\"acceleration\":16.0,\"reverseAcceleration\":23.0,\"sideAcceleration\":17.27,\"turnAcceleration\":2.617994,\"reverseTurnAcceleration\":6.283185,\"mass\":4000,\"power\":1600.0,\"dampingCoeff\":3000,\"turret_turn_speed\":1.2473868164003472,\"health\":10000,\"rank\":4,\"kickback\":1.4043,\"turretTurnAcceleration\":1.3264502315156905,\"impact_force\":1.75,\"state_null\":true}").send(battleController.client);
		new Command(Commands.EndLayoutSwitch, "BATTLE", "BATTLE").send(battleController.client);
		new Command(Commands.PrepareToSpawn, "TitanoMachina", "5390.242@1709.827@200.0@0.0").send(battleController.client);
	}
}
