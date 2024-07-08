package flashtanki.server;

import java.util.ArrayList;
import java.util.List;

import flashtanki.server.client.ClientEntity;

public class ServerProperties {
    public static String IP = "127.0.0.1";
    public static int GAME_PORT = 2351;
    public static int RESOURCE_PORT = 8080;
    public static String DELIM_COMMANDS_SYMBOL = "~dne";
    public static String DELIM_ARGUMENTS_SYMBOL = ";";
    public static boolean INVITES_ENABLED = false;
    public static List<ClientEntity> clients = new ArrayList<ClientEntity>();
}
