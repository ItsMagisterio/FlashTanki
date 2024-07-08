package flashtanki.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import flashtanki.server.logger.Logger;
import flashtanki.server.resource.ServerIdResource;
import flashtanki.server.ServerProperties;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;

public class ResourceServer {
  private static final Logger LOGGER = new Logger();
  private static final ResourceManager resourceManager;

  static {
    ResourceManager manager = null;
    try {
      manager = new ResourceManager();
    } catch (ResourceManager.ResourceManagerException e) {
      LOGGER.error("Error initializing ResourceManager: " + e.getMessage());
      System.exit(1); // Exit the application if ResourceManager initialization fails
    }
    resourceManager = manager;
  }

  public static void start() throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(ServerProperties.IP, ServerProperties.RESOURCE_PORT), 0);
    server.createContext("/", new RequestHandler());
    server.setExecutor(Executors.newFixedThreadPool(10));
    server.start();
    LOGGER.log(Logger.INFO, "Server started on port " + ServerProperties.RESOURCE_PORT);
  }

  static class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      String[] pathSegments = exchange.getRequestURI().getPath().split("/");

      if (pathSegments.length < 6) {
        sendNotFound(exchange, "Invalid URL format.");
        return;
      }

      try {
        long id = Long.parseLong(pathSegments[1]);
        long version = Long.parseLong(pathSegments[2]);
        String subVersion = pathSegments[3];
        String subId = pathSegments[4];
        String file = pathSegments[5];

        LOGGER.log(Logger.INFO, String.format("Parsed request for resource: %d/%d/%s/%s/%s", id, version, subVersion, subId, file));

        ServerIdResource resourceId = new ServerIdResource(id, version);

        Path resourcePath = resourceManager.getResource(String.format("%s/%d/%d/%s/%s/%s", resourceId.id, resourceId.version, subVersion, subId, file));

        LOGGER.log(Logger.INFO, "Looking for resource at: " + resourcePath.toString());

        if (!Files.exists(resourcePath)) {
          sendNotFound(exchange, String.format("Resource %d/%d/%s/%s/%s not found", id, version, subVersion, subId, file));
          LOGGER.log(Logger.INFO, String.format("Resource %d/%d/%s/%s/%s not found", id, version, subVersion, subId, file));
          return;
        }

        LOGGER.log(Logger.INFO, "Found resource locally at: " + resourcePath.toString());

        String contentType = getContentType(resourcePath.toFile());
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(200, Files.size(resourcePath));
        try (OutputStream os = exchange.getResponseBody(); InputStream is = Files.newInputStream(resourcePath)) {
          is.transferTo(os);
        }
        LOGGER.log(Logger.INFO, String.format("Sent resource %d/%d/%s/%s/%s", id, version, subVersion, subId, file));
      } catch (NumberFormatException | IOException e) {
        LOGGER.log(Logger.ERROR, "Error processing request: " + e.getMessage());
        sendNotFound(exchange, "Invalid resource ID or path.");
      }
    }

    private void sendNotFound(HttpExchange exchange, String message) throws IOException {
      exchange.sendResponseHeaders(404, 0);
      try (OutputStream os = exchange.getResponseBody()) {
        os.write(getNotFoundBody(message).getBytes());
      }
    }

    private String getNotFoundBody(String message) {
      return "<html><body><h1>404 Not Found</h1><p>" + message + "</p></body></html>";
    }

    private String getContentType(File file) {
      String extension = getFileExtension(file.getName());
      switch (extension.toLowerCase()) {
        case "jpg":
          return "image/jpeg";
        case "png":
          return "image/png";
        case "json":
          return "application/json";
        case "xml":
          return "application/xml";
        default:
          return "application/octet-stream";
      }
    }

    private String getFileExtension(String fileName) {
      int lastDotIndex = fileName.lastIndexOf('.');
      if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
        return fileName.substring(lastDotIndex + 1).toLowerCase();
      }
      return "";
    }
  }
}
