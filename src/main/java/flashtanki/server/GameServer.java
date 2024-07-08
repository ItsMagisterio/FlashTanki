package flashtanki.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import flashtanki.server.client.ClientEntity;
import flashtanki.server.logger.Logger;

public class GameServer {

	private static ServerSocketChannel serverChannel;
	private static final ExecutorService clientThreadPool = Executors.newCachedThreadPool();

	public static void start() {
		open();
	}

	private static void open() {
		try {
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(true);
			serverChannel.socket().bind(new InetSocketAddress(ServerProperties.IP, ServerProperties.GAME_PORT));
			Logger.log(Logger.INFO, "Game server is ready");

			while (true) {
				try {
					Socket socket = serverChannel.socket().accept();
					socket.setKeepAlive(true);
					Logger.log(Logger.INFO, "New connection!");

					ClientEntity clientEntity = new ClientEntity(socket);
					ServerProperties.clients.add(clientEntity);
					clientThreadPool.execute(clientEntity);

				} catch (IOException e) {
					Logger.log(Logger.ERROR, "Error accepting new connection: " + e.getMessage());
				}
			}
		} catch (IOException e) {
			Logger.log(Logger.ERROR, "Error starting server: " + e.getMessage());
		} finally {
			if (serverChannel != null && serverChannel.isOpen()) {
				try {
					serverChannel.close();
				} catch (IOException e) {
					Logger.log(Logger.ERROR, "Error closing server channel: " + e.getMessage());
				}
			}
		}
	}

	public static void shutdown() {
		clientThreadPool.shutdown();
		if (serverChannel != null && serverChannel.isOpen()) {
			try {
				serverChannel.close();
				Logger.log(Logger.INFO, "Game server shut down");
			} catch (IOException e) {
				Logger.log(Logger.ERROR, "Error closing server channel: " + e.getMessage());
			}
		}
	}
}
