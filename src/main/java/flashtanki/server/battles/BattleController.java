package flashtanki.server.battles;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Timer;
import java.util.TimerTask;
import flashtanki.server.client.ClientEntity;
import flashtanki.server.protocol.commands.Command;
import flashtanki.server.protocol.commands.Commands;
import flashtanki.server.resource.Resource;
import flashtanki.server.utils.JSON;

public class BattleController {
	
	public BattleModel battleModel;
	public ClientEntity client;
	public boolean userInited = false;
	
	public BattleController(ClientEntity entity, BattleModel battle) {
		this.battleModel = battle;
		this.client = entity;
		this.battleModel.users.put(entity.user.username, this);
	}
	
	public void initBattle() {
		new Command(Commands.InitBonusesData, "{\"bonuses\":[{\"lighting\":{\"attenuationBegin\":100,\"attenuationEnd\":500,\"color\":6250335,\"intensity\":1,\"time\":0},\"id\":\"nitro\",\"resourceId\":170010,\"lifeTime\":30},{\"lighting\":{\"attenuationBegin\":100,\"attenuationEnd\":500,\"color\":9348154,\"intensity\":1,\"time\":0},\"id\":\"damage\",\"resourceId\":170011,\"lifeTime\":30},{\"lighting\":{\"attenuationBegin\":100,\"attenuationEnd\":500,\"color\":7185722,\"intensity\":1,\"time\":0},\"id\":\"armor\",\"resourceId\":170006,\"lifeTime\":30},{\"lighting\":{\"attenuationBegin\":100,\"attenuationEnd\":500,\"color\":14605789,\"intensity\":1,\"time\":0},\"id\":\"health\",\"resourceId\":170009,\"lifeTime\":30},{\"lighting\":{\"attenuationBegin\":100,\"attenuationEnd\":500,\"color\":15044128,\"intensity\":1,\"time\":0},\"id\":\"gold\",\"resourceId\":170008,\"lifeTime\":30},{\"lighting\":{\"attenuationBegin\":100,\"attenuationEnd\":500,\"color\":15044128,\"intensity\":1,\"time\":0},\"id\":\"container\",\"resourceId\":170007,\"lifeTime\":30}],\"cordResource\":1000065,\"parachuteInnerResource\":170005,\"parachuteResource\":170004,\"pickupSoundResource\":269321}").send(client);
		new Command(Commands.InitBattleModel, JSON.parseInitBattleModelData(battleModel)).send(this.client);
		new Command(Commands.InitBonuses, "[]").send(client);
		try {
			new Command(Commands.InitShotsData, new String(Files.readAllBytes(Resource.get("shots-data.json")))).send(client);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.initLocal();
		this.createTank();
	}
	
	private void createTank() {
		this.battleModel.createTank(this);
	}

	public void initLocal() {
		this.userInited = true;
		if (this.battleModel.battleMode == BattleMode.Deathmatch)
		{
			new Command(Commands.InitDmModel).send(this.client);
		};
		new Command(Commands.InitGuiModel, JSON.parseInitGuiModelData(battleModel)).send(this.client);
		new Command(Commands.InitDmStatistics, JSON.parseInitDMStatistics()).send(this.client);
	}
}
