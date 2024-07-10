package flashtanki.server.protocol.commands.handlers;

import flashtanki.server.client.ClientEntity;
import flashtanki.server.protocol.commands.Command;
import flashtanki.server.protocol.commands.CommandHandler;
import flashtanki.server.protocol.commands.Commands;
import flashtanki.server.utils.JSON;

public class BattleHandler implements CommandHandler {

	@Override
	public void handle(ClientEntity client, String command, String[] args) {
		if (command.startsWith(Commands.Ping.command))
		{
			if (!client.battleController.userInited)
			{
				client.battleController.start();
			}
			new Command(Commands.Pong).send(client);
		};
		if (command.startsWith(Commands.GetInitDataLocalTank.command))
		{
			client.battleController.initLocal();
		};
		if (command.startsWith(Commands.BattleChatServer.command))
		{
			client.battleController.battleModel.send2Battle(new Command(Commands.BattleChat, JSON.parseBattleMessage(client.battleController, args[0], Boolean.valueOf(args[1]))));
		};
	}
}
