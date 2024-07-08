package flashtanki.server;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import flashtanki.server.client.ClientEntity;
import flashtanki.server.logger.Logger;

public class GameServer {
	
   private static ServerSocketChannel serverChannel;
	
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
          	 Socket socket = serverChannel.socket().accept();
          	 socket.setKeepAlive(true);
		     Logger.log(Logger.INFO, "New connection!");
		     ClientEntity ce = new ClientEntity(socket);
		     ServerProperties.clients.add(ce);
		     new Thread(ce).start();
	      }
	   } catch (Exception e) {
		   e.getStackTrace();
	   }
   }
}
