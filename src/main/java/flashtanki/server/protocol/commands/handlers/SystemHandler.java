package flashtanki.server.protocol.commands.handlers;

import flashtanki.server.ServerProperties;
import flashtanki.server.client.ClientEntity;
import flashtanki.server.client.Dependency;
import flashtanki.server.client.Localization;
import flashtanki.server.protocol.commands.Command;
import flashtanki.server.protocol.commands.CommandHandler;
import flashtanki.server.protocol.commands.Commands;
import flashtanki.server.utils.JSON;

public class SystemHandler implements CommandHandler {

	@Override
	public void handle(ClientEntity client, String command, String[] args) {
		if (command.startsWith(Commands.GetAesData.command)) {
			new Command(Commands.InitRegistrationModel, JSON.parseInitRegistrationModelData()).send(client);
			new Command(Commands.InitLocale, Localization.parse(args[0])).send(client);
			client.dependencyUtils.loadDependency(client, "auth-untrusted.json", () -> {
				client.dependencyUtils.loadDependency(client, "auth.json", () -> {
					new Command(Commands.InitInviteModel, ServerProperties.INVITES_ENABLED).send(client);
					new Command(Commands.MainResourcesLoaded).send(client);
				});
			});
		}

		if (command.startsWith(Commands.DependenciesLoaded.command)) {
			client.dependencyUtils.mark(Integer.valueOf(args[0]));
		}
	}
}
