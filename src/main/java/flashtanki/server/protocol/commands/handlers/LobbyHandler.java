package flashtanki.server.protocol.commands.handlers;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;

import flashtanki.server.battles.BattleController;
import flashtanki.server.battles.BattleMode;
import flashtanki.server.battles.BattleModel;
import flashtanki.server.battles.BattleProcessor;
import flashtanki.server.battles.BattleProperties;
import flashtanki.server.battles.BattleProperty;
import flashtanki.server.client.ClientEntity;
import flashtanki.server.protocol.commands.Command;
import flashtanki.server.protocol.commands.CommandHandler;
import flashtanki.server.protocol.commands.Commands;
import flashtanki.server.utils.JSON;

public class LobbyHandler implements CommandHandler {

	@Override
	public void handle(ClientEntity client, String command, String[] args) {
		if (command.startsWith(Commands.CreateBattle.command))
		{
			JSONObject data = JSON.parse(args[0]);
			Map<BattleProperty<?>, Object> properties = new HashMap<>();
			BattleProperties battleProperties = new BattleProperties(properties);
			battleProperties.set(BattleProperty.TimeLimit, ((Long)data.get("timeLimitInSec")).intValue());
			battleProperties.set(BattleProperty.RearmingEnabled, (Boolean)data.get("reArmorEnabled"));
			battleProperties.set(BattleProperty.AutoBalance, (Boolean)data.get("autoBalance"));
			battleProperties.set(BattleProperty.WithoutBonuses, (Boolean)data.get("withoutBonuses"));
			battleProperties.set(BattleProperty.ParkourMode, (Boolean)data.get("parkourMode"));
			battleProperties.set(BattleProperty.privateBattle, (Boolean)data.get("privateBattle"));
			battleProperties.set(BattleProperty.WithoutCrystals, (Boolean)data.get("withoutCrystals"));
			battleProperties.set(BattleProperty.FriendlyFireEnabled, (Boolean)data.get("friendlyFire"));
			battleProperties.set(BattleProperty.WithoutSupplies, (Boolean)data.get("withoutSupplies"));
			battleProperties.set(BattleProperty.ProBattle, (Boolean)data.get("proBattle"));
			battleProperties.set(BattleProperty.WithoutBonuses, (Boolean)data.get("withoutBonuses"));
			battleProperties.set(BattleProperty.MinRank, /*((Long)data.get("minRank")).intValue()*/1);
			battleProperties.set(BattleProperty.MaxRank, /*((Long)data.get("maxRank")).intValue()*/30);
			battleProperties.set(BattleProperty.MaxPeople, ((Long)data.get("maxPeopleCount")).intValue());
			battleProperties.set(BattleProperty.ScoreLimit, ((Long)data.get("scoreLimit")).intValue());
			BattleModel battleModel = new BattleModel((String)data.get("name"), BattleProcessor.generateId(), battleProperties, BattleMode.getByName((String)data.get("battleMode")));
			BattleProcessor.addBattle(battleModel);
			new Command(Commands.AddBattle, JSON.parseBattleData(battleModel)).send(client);
			this.showBattleInfo(client, battleModel);
		};
		if (command.startsWith(Commands.SelectBattle.command))
		{
			String battleId = args[0];
			BattleModel battleModel = BattleProcessor.getBattleById(battleId);
            this.showBattleInfo(client, battleModel);
		};
		if (command.startsWith(Commands.Fight.command))
		{
			client.initBattle();
			BattleController controller = new BattleController(client, client.selectedBattle);
			client.battleController = controller;
			controller.initBattle();
		};
		if (command.startsWith(Commands.ShowSettings.command))
		{
			new Command(Commands.ClientShowSettings, JSON.parseShowSettingsData()).send(client);
		};
		if (command.startsWith(Commands.CheckPasswordIsSet.command))
		{
			new Command(Commands.PasswordIsSet).send(client);
		};
		if (command.startsWith(Commands.SubscribeUserUpdate.command))
		{
			new Command(Commands.NotifyUserOnline, "{\"userId\":\"TitanoMachina\",\"online\":true}").send(client);
			new Command(Commands.NotifyPlayerJoinBattle, "{\"userId\":\"TitanoMachina\",\"battleId\":\"" + client.battleController.battleModel.battleId + "\",\"mapName\":\"Sandbox DM\",\"mode\":\"DM\",\"privateBattle\":false,\"proBattle\":false,\"minRank\":1,\"maxRank\":30}").send(client);
			new Command(Commands.NotifyUserPremium, "{\"userId\":\"TitanoMachina\",\"premiumTimeLeftInSeconds\":0}").send(client);
			new Command(Commands.NotifyUserRank, "{\"userId\":\"TitanoMachina\",\"rank\":20}").send(client);
		};
	}
	
	private void showBattleInfo(ClientEntity client, BattleModel battle) {
		client.selectedBattle = battle;
		new Command(Commands.ClientSelectBattle, battle.battleId).send(client);
		new Command(Commands.ShowBattleInfo, JSON.parseBattleInfo(battle)).send(client);
	}

}
