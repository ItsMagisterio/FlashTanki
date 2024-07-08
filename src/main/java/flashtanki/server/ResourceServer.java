package flashtanki.server;

import flashtanki.server.logger.Logger;
import flashtanki.server.resource.Resource;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ResourceServer {
	    private static final String STATIC_ROOT = "./static";

	    public static void start() {
	        HttpServer server = null;
			try {
				server = HttpServer.create(new InetSocketAddress(ServerProperties.IP, ServerProperties.RESOURCE_PORT), 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        server.createContext("/", new RequestHandler());
	        server.setExecutor(null); // creates a default executor
	        server.start();
	        System.out.println("Server started on port " + ServerProperties.RESOURCE_PORT);
	    }

	    static class RequestHandler implements HttpHandler {
	        @Override
	        public void handle(HttpExchange exchange) throws IOException {
	            System.out.println("ХУЙ");
	        }

	        private void sendFile(File file, OutputStream out) {
	            try (InputStream fileIn = new FileInputStream(file)) {
	                byte[] buffer = new byte[1024];
	                int bytesRead;
	                while ((bytesRead = fileIn.read(buffer)) != -1) {
	                    out.write(buffer, 0, bytesRead);
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}