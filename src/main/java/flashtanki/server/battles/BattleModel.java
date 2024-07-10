package flashtanki.server.battles;

import java.util.HashMap;

import flashtanki.server.protocol.commands.Command;
import flashtanki.server.protocol.commands.Commands;
import flashtanki.server.utils.JSON;

public class BattleModel {
	
	public int incration = 1;
	public String name;
	public String battleId;
	public BattleProperties battleProperties;
	public BattleMode battleMode;
	public HashMap<String, BattleController> users = new HashMap<String, BattleController>();
	public HashMap<String, Object> bonuses = new HashMap<String, Object>();
	public FundManager fundManager;
	
	public BattleModel(String name, String battleid, BattleProperties battleproperties, BattleMode mode) {
		this.name = name;
		this.battleId = battleid;
		this.battleProperties = battleproperties;
		this.battleMode = mode;
		this.fundManager = new FundManager(this);
	}

	public void createTank(BattleController battleController) {
		new Command(Commands.InitTank, JSON.parseInitTankData(battleController.tank)).send(battleController.client);
		new Command(Commands.EndLayoutSwitch, "BATTLE", "BATTLE").send(battleController.client);
		new Command(Commands.PrepareToSpawn, battleController.client.user.username, "14000.0@-9000.0@1100.0@10000.070501591499442E-16").send(battleController.client);
		new Command(Commands.SpawnTank, JSON.parseSpawnTankData(battleController.tank)).send(battleController.client);
		new Command(Commands.ActivateTank, battleController.client.user.username).send(battleController.client);
		this.initOtherTanks(battleController);
	}

	private void initOtherTanks(BattleController battleController) {
		for (BattleController controller : this.users.values()) {
			if (controller != battleController) {
				new Command(Commands.InitTank, JSON.parseInitTankData(controller.tank)).send(battleController.client);
			}
		}
	}

	public void send2Battle(Command command) {
		for (BattleController user : this.users.values()) {
			if (user.userInited) {
				command.send(user.client);
			}
		}
	}
}
