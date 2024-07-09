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

public class ResourceServer {
  private static final String STATIC_ROOT = "static";
  private static final String ORIGINAL_PACK_NAME = "original";
  private static final HttpClient CLIENT = HttpClient.newBuilder().build();

  public static void start() throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(ServerProperties.IP, ServerProperties.RESOURCE_PORT), 0);
    server.createContext("/", new RequestHandler());
    server.setExecutor(Executors.newFixedThreadPool(10));
    server.start();
    Logger.log(Logger.INFO, "Server started on port " + ServerProperties.RESOURCE_PORT);
  }

  static class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      String[] pathSegments = exchange.getRequestURI().getPath().split("/");
      if (pathSegments.length < 7) {
        sendNotFound(exchange, "Invalid URL format.");
        return;
      }

      String id1 = pathSegments[2];
      String id2 = pathSegments[3];
      String id3 = pathSegments[4];
      String id4 = pathSegments[5];
      String version = pathSegments[6];
      String file = pathSegments[7];

      Logger.log(Logger.INFO, String.format("Received request for resource: %s/%s/%s/%s/%s/%s", id1, id2, id3, id4, version, file));

      ServerIdResource resourceId;
      try {
        resourceId = ResourceUtils.decodeId(Arrays.asList(id1, id2, id3, id4, version));
      } catch (Exception e) {
        Logger.log(Logger.ERROR, "Failed to decode resource ID: " + e.getMessage());
        sendNotFound(exchange, "Invalid resource ID.");
        return;
      }

      Path resourcePath = getResourcePath(resourceId, version, file);
      File resource = resourcePath.toFile();

      if (!resource.exists()) {
        Logger.log(Logger.ERROR, "Resource not found.");
        sendNotFound(exchange, "Resource not found.");
        return;
      }

      String contentType = getContentType(resource);
      exchange.getResponseHeaders().set("Content-Type", contentType);
      exchange.sendResponseHeaders(200, resource.length());
      try (OutputStream os = exchange.getResponseBody(); InputStream is = new FileInputStream(resource)) {
        is.transferTo(os);
      }
      Logger.log(Logger.INFO, String.format("Sent resource %s:%s/%s", resourceId.id, version, file));
    }

    private Path getResourcePath(ServerIdResource resourceId, String version, String file) {
      int[] versionOffsets = {0, -4, -2};
      for (int offset : versionOffsets) {
        Path path = Resource.get(String.format("%s/%s/%s/%d/%s", STATIC_ROOT, ORIGINAL_PACK_NAME, resourceId.id, Integer.parseInt(version) + offset, file));
        if (path.toFile().exists()) {
          return path;
        }
      }
      return Paths.get("");
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
      String url = String.format("http://95.164.47.62:8080/%s/%s/%s", ResourceUtils.encodeId(resourceId), version, file);
      HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
      HttpResponse<InputStream> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofInputStream());

      if (response.statusCode() == 200) {
        Logger.log(Logger.INFO, String.format("Downloaded original resource: %s/%s/%s", resourceId, version, file));
        return response.body();
      }
      if (response.statusCode() == 404) {
        Logger.log(Logger.INFO, String.format("Original resource not found: %s/%s/%s", resourceId, version, file));
        return null;
      }
      throw new IOException(String.format("Failed to download resource %s:%s/%s. Status code: %d", resourceId, version, file, response.statusCode()));
    }

    private String getContentType(File file) {
      return ResourceUtils.getContentType(file.getName());
    }
  }
}
