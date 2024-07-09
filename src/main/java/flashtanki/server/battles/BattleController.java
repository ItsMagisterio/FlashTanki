package flashtanki.server.battles;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.simple.JSONObject;
import flashtanki.server.battles.statistics.UserStatistics;
import flashtanki.server.client.ClientEntity;
import flashtanki.server.protocol.commands.Command;
import flashtanki.server.protocol.commands.Commands;
import flashtanki.server.resource.Resource;
import flashtanki.server.utils.JSON;
import flashtanki.server.utils.RankUtils;

public class BattleController {
	
	public BattleModel battleModel;
	public ClientEntity client;
	public boolean userInited = false;
	public UserStatistics stat;
	
	public BattleController(ClientEntity entity, BattleModel battle) {
		this.battleModel = battle;
		this.client = entity;
		this.battleModel.users.put(entity.user.username, this);
		this.stat = new UserStatistics(0, 0, 0);
	}
	
	public void initBattle() {
        new Command(Commands.LoadResources, "{\"resources\":[{\"idhigh\":0,\"idlow\":286895,\"versionhigh\":0,\"versionlow\":5,\"type\":8,\"lazy\":false},{\"idhigh\":0,\"idlow\":426360,\"versionhigh\":0,\"versionlow\":2,\"type\":8,\"lazy\":false},{\"idhigh\":0,\"idlow\":344680,\"versionhigh\":0,\"versionlow\":2,\"type\":8,\"lazy\":false},{\"idhigh\":0,\"idlow\":421539,\"versionhigh\":0,\"versionlow\":2,\"type\":8,\"lazy\":false},{\"idhigh\":0,\"idlow\":894006,\"versionhigh\":0,\"versionlow\":2,\"type\":8,\"lazy\":false},{\"idhigh\":0,\"idlow\":644412,\"versionhigh\":0,\"versionlow\":1,\"type\":8,\"lazy\":false},{\"idhigh\":0,\"idlow\":698047,\"versionhigh\":0,\"versionlow\":21,\"type\":8,\"lazy\":false},{\"idhigh\":0,\"idlow\":761948,\"versionhigh\":0,\"versionlow\":2,\"type\":8,\"lazy\":false},{\"idhigh\":0,\"idlow\":768697,\"versionhigh\":0,\"versionlow\":1,\"type\":8,\"lazy\":false}]}").send(client);
        new Command(Commands.LoadResources, "{\"resources\":[{\"idhigh\":0,\"idlow\":45572,\"versionhigh\":0,\"versionlow\":1,\"type\":10,\"lazy\":false},{\"idhigh\":0,\"idlow\":57735,\"versionhigh\":0,\"versionlow\":1,\"type\":10,\"lazy\":false},{\"idhigh\":0,\"idlow\":268412,\"versionhigh\":0,\"versionlow\":1,\"type\":10,\"lazy\":false},{\"idhigh\":0,\"idlow\":31494,\"versionhigh\":0,\"versionlow\":1,\"type\":10,\"lazy\":false},{\"idhigh\":0,\"idlow\":927961,\"versionhigh\":0,\"versionlow\":1,\"type\":10,\"lazy\":false},{\"idhigh\":0,\"idlow\":987391,\"versionhigh\":0,\"versionlow\":1,\"type\":10,\"lazy\":false}]}").send(client);
        new Command(Commands.LoadResources, "{\"resources\":[{\"idhigh\":0,\"idlow\":336728,\"versionhigh\":0,\"versionlow\":2,\"type\":7,\"lazy\":false}]}").send(client);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					new Command(Commands.InitShotsData, new String(Files.readAllBytes(Resource.get("shots-data.json")))).send(client);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Command(Commands.InitBonusesData, "{\"bonuses\":[{\"lighting\":{\"attenuationBegin\":100,\"attenuationEnd\":500,\"color\":6250335,\"intensity\":1,\"time\":0},\"id\":\"nitro\",\"resourceId\":170010,\"lifeTime\":30},{\"lighting\":{\"attenuationBegin\":100,\"attenuationEnd\":500,\"color\":9348154,\"intensity\":1,\"time\":0},\"id\":\"damage\",\"resourceId\":170011,\"lifeTime\":30},{\"lighting\":{\"attenuationBegin\":100,\"attenuationEnd\":500,\"color\":7185722,\"intensity\":1,\"time\":0},\"id\":\"armor\",\"resourceId\":170006,\"lifeTime\":30},{\"lighting\":{\"attenuationBegin\":100,\"attenuationEnd\":500,\"color\":14605789,\"intensity\":1,\"time\":0},\"id\":\"health\",\"resourceId\":170009,\"lifeTime\":30},{\"lighting\":{\"attenuationBegin\":100,\"attenuationEnd\":500,\"color\":15044128,\"intensity\":1,\"time\":0},\"id\":\"gold\",\"resourceId\":170008,\"lifeTime\":30},{\"lighting\":{\"attenuationBegin\":100,\"attenuationEnd\":500,\"color\":15044128,\"intensity\":1,\"time\":0},\"id\":\"container\",\"resourceId\":170007,\"lifeTime\":30}],\"cordResource\":1000065,\"parachuteInnerResource\":170005,\"parachuteResource\":170004,\"pickupSoundResource\":269321}").send(client);
				new Command(Commands.InitBattleModel, JSON.parseInitBattleModelData(battleModel)).send(client);
				new Command(Commands.InitBonuses, "[]").send(client);
				initLocal();
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						createTank();	
					}
				}, 3000);	
			}
		}, 4000);
	}
	
	private void createTank() {
		this.battleModel.createTank(this);
	}

	public void initLocal() {
		List<JSONObject> userStats = new ArrayList<JSONObject>();
		for (BattleController player : this.battleModel.users.values()) {
			userStats.add(JSON.parseUserStat(player));
		}
		this.userInited = true;
		if (this.battleModel.battleMode == BattleMode.Deathmatch)
		{
			new Command(Commands.InitDmModel).send(this.client);
		};
		new Command(Commands.InitGuiModel, JSON.parseInitGuiModelData(battleModel)).send(this.client);
		new Command(Commands.InitDmStatistics, JSON.parseInitDMStatistics(userStats)).send(this.client);
	}
}
