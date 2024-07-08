package flashtanki.server;

import java.io.IOException;

import flashtanki.server.battles.BattleProcessor;
import flashtanki.server.database.Repositories;
import flashtanki.server.logger.Logger;
import flashtanki.server.utils.RankUtils;

public class Main {

	public static void main(String[] args) throws IOException {
		RankUtils.init();
		Repositories.init();
		BattleProcessor.init();
		ResourceServer.start();
		GameServer.start();
	}
}
