package flashtanki.server.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Resource {
    private static Path resourceDirectory = null;

    public static Path get(String path) {
        Path directory = Paths.get("data");
        if (!Files.exists(directory)) {
            directory = Paths.get("../data");
        }
        if (!Files.exists(directory)) {
            directory = Paths.get("src/main/resources/data");
        }
        if (!Files.exists(directory)) {
            directory = Paths.get("../src/main/resources/data");
        }
        if (!Files.exists(directory)) {
            System.out.println("НАЙН");
        }

        resourceDirectory = directory;
        return resourceDirectory.resolve(path);
    }
}