package flashtanki.server;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceManager {
    private final Path resourceDirectory;

    public ResourceManager() throws ResourceManagerException {
        Path directory = Paths.get("data");
        if (!Files.exists(directory)) directory = Paths.get("../data"); // Gradle distribution / jar
        if (!Files.exists(directory)) directory = Paths.get("src/main/resources/data"); // Started from IntelliJ IDEA, default working directory
        if (!Files.exists(directory)) directory = Paths.get("../src/main/resources/data"); // Started from IntelliJ IDEA, 'out' working directory
        if (!Files.exists(directory)) throw new ResourceManagerException("Cannot find runtime resources directory");

        resourceDirectory = directory;
    }

    public Path getResource(String path) {
        return resourceDirectory.resolve(path);
    }

    public static class ResourceManagerException extends Exception {
        public ResourceManagerException(String message) {
            super(message);
        }
    }
}
