package flashtanki.server;

import flashtanki.server.logger.Logger;
import flashtanki.server.resource.Resource;
import flashtanki.server.resource.ServerIdResource;
import flashtanki.server.utils.ResourceUtils;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.Executors;
import com.sun.net.httpserver.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ResourceServer {
  private static final String STATIC_ROOT = "./static";
  private static final String ORIGINAL_PACK_NAME = "original";
  private static final HttpClient CLIENT = HttpClient.newBuilder().build();
  private static final Logger LOGGER = new Logger(); // Assuming a Logger class exists

  public static void start() throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(ServerProperties.IP, ServerProperties.RESOURCE_PORT), 0);
    server.createContext("/", new RequestHandler());
    server.setExecutor(Executors.newFixedThreadPool(10)); // creates a thread pool with 10 threads
    server.start();
    LOGGER.log(Logger.INFO, "Server started on port " + ServerProperties.RESOURCE_PORT);
  }

  static class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      String[] pathSegments = exchange.getRequestURI().getPath().split("/");
      if (pathSegments.length < 7) {
        sendNotFound(exchange, "Invalid URL format.");
        return;
      }

      String id1 = pathSegments[1];
      String id2 = pathSegments[2];
      String id3 = pathSegments[3];
      String id4 = pathSegments[4];
      String version = pathSegments[5];
      String file = pathSegments[6];

      LOGGER.log(Logger.INFO, String.format("Received request for resource: %s/%s/%s/%s/%s", id1, id2, id3, id4, version, file));

      ServerIdResource resourceId;
      try {
        resourceId = ResourceUtils.decodeId(Arrays.asList(id1, id2, id3, id4, version));
      } catch (Exception e) {
        LOGGER.log(Logger.ERROR, "Failed to decode resource ID: " + e.getMessage());
        sendNotFound(exchange, "Invalid resource ID.");
        return;
      }

      Path resourcePath = Paths.get(STATIC_ROOT, ORIGINAL_PACK_NAME, Long.toString(resourceId.id), version, file);
      File resource = resourcePath.toFile();

      if (!resource.exists()) {
        LOGGER.log(Logger.INFO, "Resource not found locally, attempting to download: " + resourcePath);
        InputStream stream = null;
        try {
          stream = downloadOriginal(resourceId, version, file);
        } catch (IOException | InterruptedException e) {
          LOGGER.log(Logger.ERROR, "Error downloading resource: " + e.getMessage());
        }

        if (stream == null) {
          sendNotFound(exchange, String.format("Resource %s:%s/%s not found", resourceId, version, file));
          LOGGER.log(Logger.INFO, String.format("Resource %s:%s/%s not found", resourceId, version, file));
          return;
        }

        if (!resource.getParentFile().exists()) {
          resource.getParentFile().mkdirs();
        }
        try (OutputStream output = new FileOutputStream(resource)) {
          stream.transferTo(output);
        }
      }

      String contentType = getContentType(resource);
      exchange.getResponseHeaders().set("Content-Type", contentType);
      exchange.sendResponseHeaders(200, resource.length());
      try (OutputStream os = exchange.getResponseBody(); InputStream is = new FileInputStream(resource)) {
        is.transferTo(os);
      }
      LOGGER.log(Logger.INFO, String.format("Sent resource %s:%s/%s", resourceId, version, file));
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

    private InputStream downloadOriginal(ServerIdResource resourceId, String version, String file) throws IOException, InterruptedException {
      String url = String.format("http://146.59.110.103/%s/%s/%s", ResourceUtils.encodeId(resourceId), version, file);
      HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
      HttpResponse<InputStream> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofInputStream());

      if (response.statusCode() == 200) {
        LOGGER.log(Logger.INFO, String.format("Downloaded original resource: %s/%s/%s", resourceId, version, file));
        return response.body();
      }
      if (response.statusCode() == 404) {
        LOGGER.log(Logger.INFO, String.format("Original resource not found: %s/%s/%s", resourceId, version, file));
        return null;
      }
      throw new IOException(String.format("Failed to download resource %s:%s/%s. Status code: %d", resourceId, version, file, response.statusCode()));
    }

    private String getContentType(File file) {
      String extension = getFileExtension(file.getName());
      switch (extension) {
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
        return fileName.substring(lastDotIndex + 1);
      }
      return "";
    }
  }
}
