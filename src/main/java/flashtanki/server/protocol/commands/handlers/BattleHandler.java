package flashtanki.server.protocol.commands.handlers;

import flashtanki.server.client.ClientEntity;
import flashtanki.server.protocol.commands.Command;
import flashtanki.server.protocol.commands.CommandHandler;
import flashtanki.server.protocol.commands.Commands;

public class BattleHandler implements CommandHandler {

	@Override
	public void handle(ClientEntity client, String command, String[] args) {
		if (command.startsWith(Commands.Ping.command))
		{
			if (!client.battleController.userInited)
			{
				//client.battleController.init();
			}
			//new Command(Commands.Pong).send(client);
		};
		if (command.startsWith(Commands.GetInitDataLocalTank.command))
		{
			
		};
	}
}
