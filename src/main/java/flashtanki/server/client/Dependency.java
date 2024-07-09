package flashtanki.server.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		Dependency dependency = new Dependency(0, new CompletableFuture<Void>());
		return dependency;
	}
	
    public void createAndLoadDependency(ClientEntity client, String file) {
    	Path resource = Resource.get("resources/" + file);
    	this.id++;
    	try {
			new Command(Commands.LoadResources, new String(Files.readAllBytes(resource)), id).send(client);
			//this.deferred.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void markDependency(int id) {
    	//this.deferred.complete(null);
    	Logger.log(Logger.INFO, "Dependency " + id + " is loaded");
    }
}
