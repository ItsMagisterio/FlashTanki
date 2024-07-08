package flashtanki.server.client;

import java.io.IOException;
import java.nio.file.Files;

import flashtanki.server.resource.Resource;

public class Localization {
    public static String parse(String language) {
    	try {
			return (new String(Files.readAllBytes(Resource.get("lang/" + language + ".json"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return "";
    }
}
