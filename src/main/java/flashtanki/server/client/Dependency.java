package flashtanki.server.client;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import flashtanki.server.logger.Logger;
import flashtanki.server.protocol.commands.Command;
import flashtanki.server.protocol.commands.Commands;
import flashtanki.server.resource.Resource;

public class Dependency {
	private CompletableFuture<Void> deferred;
	private Runnable runnable;

	public Dependency(int id, CompletableFuture<Void> deferred) {
		this.deferred = deferred;
	}

	public static Dependency create() {
		return new Dependency(0, new CompletableFuture<>());
	}

	public void loadDependency(ClientEntity client, int id, String file, Runnable afterLoad) {
		Path resource = Resource.get("resources/" + file);
		try {
			new Command(Commands.LoadResources, new String(Files.readAllBytes(resource)), id).send(client);
			if (afterLoad != null) {
				this.runnable = afterLoad;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void markDependency() {
		if (this.runnable != null) {
			this.deferred.thenRun(this.runnable);
			this.deferred.complete(null);
		}
	}
}
