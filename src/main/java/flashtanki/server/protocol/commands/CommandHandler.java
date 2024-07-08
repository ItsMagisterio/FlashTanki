package flashtanki.server.protocol.commands;

import flashtanki.server.client.ClientEntity;

public interface CommandHandler {
    void handle(ClientEntity client, String command, String[] args);
}
