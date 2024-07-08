package flashtanki.server.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import flashtanki.server.logger.Logger;
import flashtanki.server.protocol.commands.Command;
import flashtanki.server.protocol.commands.Commands;
import flashtanki.server.resource.Resource;

public class Dependency {
	private static int id = 0;
	
    public static void createAndLoadDependency(ClientEntity client, String file) {
    	Path resource = Resource.get("resources/" + file);
    	id++;
    	try {
			new Command(Commands.LoadResources, new String(Files.readAllBytes(resource)), id).send(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void markDependency(int id) {
    	Logger.log(Logger.INFO, "Dependency " + id + " is loaded");
    }
}
