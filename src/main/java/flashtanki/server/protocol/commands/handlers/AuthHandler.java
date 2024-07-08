package flashtanki.server.protocol.commands.handlers;

import flashtanki.server.client.ClientEntity;
import flashtanki.server.database.Repositories;
import flashtanki.server.protocol.commands.Command;
import flashtanki.server.protocol.commands.CommandHandler;
import flashtanki.server.protocol.commands.Commands;

public class AuthHandler implements CommandHandler {

	@Override
	public void handle(ClientEntity client, String command, String[] args) {
		if (command.startsWith(Commands.Login.command))
		{
			new Command(Commands.AuthAccept).send(client);
			client.user = Repositories.userRepository.getUser("TitanoMachina");
			client.initLobby();
		};
	}

}
