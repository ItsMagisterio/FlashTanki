package flashtanki.server.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import flashtanki.server.ServerProperties;
import flashtanki.server.battles.BattleProcessor;
import flashtanki.server.logger.Logger;
import flashtanki.server.protocol.ProtocolUtils;
import flashtanki.server.protocol.commands.Command;
import flashtanki.server.protocol.commands.CommandHandler;
import flashtanki.server.protocol.commands.Commands;
import flashtanki.server.protocol.commands.handlers.AuthHandler;
import flashtanki.server.protocol.commands.handlers.BattleHandler;
import flashtanki.server.protocol.commands.handlers.LobbyHandler;
import flashtanki.server.protocol.commands.handlers.SystemHandler;
import flashtanki.server.user.User;
import flashtanki.server.utils.JSON;
import flashtanki.server.utils.RankUtils;
import flashtanki.server.battles.BattleController;
import flashtanki.server.battles.BattleLimit;
import flashtanki.server.battles.BattleModel;
import flashtanki.server.battles.Map;

public class ClientEntity implements Runnable {
	
	private Socket socket;
	public User user;
	public BattleModel selectedBattle;
	public BattleController battleController;
	
	public ClientEntity(Socket socket) {
		this.socket = socket;
		this.user = null;
	}
	
	public void send(String packet) {
		packet = packet + ServerProperties.DELIM_COMMANDS_SYMBOL;
		try {
			this.socket.getOutputStream().write(packet.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
 	      byte[] arrayOfByte = new byte[1024]; 
 	      try {
 	         InputStream inputStream = this.socket.getInputStream();
 	         String data = null;
 	         if (inputStream.available() > 0) {
 	            int bytes = inputStream.read(arrayOfByte);
 	            data = new String(arrayOfByte);
 	         }
 	         if (data == null || data == "") {
 	        	 continue;
 	         }
 	         String decrypted = ProtocolUtils.decrypt(data, 1);
             if (decrypted != null || decrypted != "") {
            	 Logger.log(Logger.INFO, "Received new command: " + decrypted);
            	 this.sendToHandlers(decrypted);
             }
	      } catch (Exception e) {
		     e.getStackTrace();
	      }
	   }  
	}

	private void sendToHandlers(String decrypted) {
		CommandHandler handler = null;
		String[] args = ProtocolUtils.getArgsFromPacket(decrypted);
		String name = ProtocolUtils.getNameFromPacket(decrypted);
		String type = ProtocolUtils.getTypeFromPacket(decrypted);
		switch (type) {
		   case "system":
			   handler = new SystemHandler();
			   handler.handle(this, name, args);
			   return;
		   case "auth":
			   handler = new AuthHandler();
			   handler.handle(this, name, args);
			   return;
		   case "lobby":
		   case "battle_create":
		   case "battle_select":
			   handler = new LobbyHandler();
			   handler.handle(this, name, args);
			   return;
		   case "battle":
			   handler = new BattleHandler();
			   handler.handle(this, name, args);
			   return;
		}
	}

	public void initLobby() {
		new Command(Commands.StartLayoutSwitch, "BATTLE_SELECT").send(this);
		Dependency.createAndLoadDependency(this, "lobby.json");
		new Command(Commands.InitPremium, "{\"left_time\":0,\"needShowNotificationCompletionPremium\":false,\"needShowWelcomeAlert\":false,\"reminderCompletionPremiumTime\":86400,\"wasShowAlertForFirstPurchasePremium\":false,\"wasShowReminderCompletionPremium\":false}").send(this);
		new Command(Commands.InitPanel, JSON.parseInitPanelData(this.user.username, this.user.crystals, null, false, this.user.getNextScore(), 0, RankUtils.getNumberRank(this.user.rank), 0, this.user.score, 0, false, 0, "")).send(this);
		this.initBattleSelect();
		new Command(Commands.EndLayoutSwitch, "BATTLE_SELECT", "BATTLE_SELECT").send(this);
	}
	
    private void initBattleSelect() {
    	List<BattleLimit> limits = new ArrayList<BattleLimit>();
    	List<Map> maps = new ArrayList<Map>();
        limits.add(new BattleLimit("DM", 999, 59940));
        limits.add(new BattleLimit("TDM", 999, 59940));
        limits.add(new BattleLimit("CTF", 999, 59940));
        limits.add(new BattleLimit("CP", 999, 59940));
        maps.add(new Map());
    	new Command(Commands.InitBattleCreate, JSON.parseInitBattleCreateData(limits, maps)).send(this);
		new Command(Commands.InitBattleSelect, JSON.parseInitBattleSelectData(BattleProcessor.battles)).send(this);
	}

	public void addScore(int score) {
    	this.user.score += score;
    	new Command(Commands.SetScore, this.user.score).send(this);
    }
    
    public void addCrystals(int crystalls) {
    	this.user.crystals += crystalls;
    	new Command(Commands.SetCrystals, this.user.crystals).send(this);
    }

	public void initBattle() {
	    new Command(Commands.StartLayoutSwitch, "BATTLE").send(this);
	    new Command(Commands.UnloadBattleSelect).send(this);
	    new Command(Commands.StartBattle).send(this);
	    //new Command(Commands.UnloadChat).send(this);
	}
}
