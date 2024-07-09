package flashtanki.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
				acceptClient();
			}
		} catch (IOException e) {
			Logger.log(Logger.ERROR, "Error starting server: " + e.getMessage());
		} finally {
			closeServerChannel();
		}
	}

	private static void acceptClient() {
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

	private static void closeServerChannel() {
		if (serverChannel != null && serverChannel.isOpen()) {
			try {
				serverChannel.close();
				Logger.log(Logger.INFO, "Server channel closed");
			} catch (IOException e) {
				Logger.log(Logger.ERROR, "Error closing server channel: " + e.getMessage());
			}
		}
	}

	public static void shutdown() {
		try {
			clientThreadPool.shutdown();
			if (!clientThreadPool.awaitTermination(30, TimeUnit.SECONDS)) {
				clientThreadPool.shutdownNow();
				if (!clientThreadPool.awaitTermination(30, TimeUnit.SECONDS)) {
					Logger.log(Logger.ERROR, "Client thread pool did not terminate");
				}
			}
		} catch (InterruptedException ie) {
			clientThreadPool.shutdownNow();
			Thread.currentThread().interrupt();
		}
		closeServerChannel();
		Logger.log(Logger.INFO, "Game server shut down");
	}
}
