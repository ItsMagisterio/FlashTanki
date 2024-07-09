package flashtanki.server.client;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import flashtanki.server.logger.Logger;
import flashtanki.server.protocol.commands.Command;
import flashtanki.server.protocol.commands.Commands;
import flashtanki.server.resource.Resource;

public class Dependency {
	private int id = 0;
	private CompletableFuture<Void> deferred;

	public Dependency(int id, CompletableFuture<Void> deferred) {
		this.id = id;
		this.deferred = deferred;
	}

	public static Dependency create() {
		return new Dependency(0, new CompletableFuture<>());
	}

	public void loadDependency(ClientEntity client, String file) {
		loadDependency(client, file, null);
	}

	public void loadDependency(ClientEntity client, String file, Runnable afterLoad) {
		Path resource = Resource.get("resources/" + file);
		this.id++;
		try {
			new Command(Commands.LoadResources, new String(Files.readAllBytes(resource)), id).send(client);
			if (afterLoad != null) {
				this.deferred.thenRun(afterLoad);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void markDependency(int id) {
		this.deferred.complete(null);
		Logger.log(Logger.INFO, "Dependency " + id + " is loaded");
	}
}
