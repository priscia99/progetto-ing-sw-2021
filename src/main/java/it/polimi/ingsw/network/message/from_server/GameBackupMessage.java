package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientGame;
import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.service_message.ServiceMessage;
import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.server.model.game.Game;

import java.io.Serializable;

public class GameBackupMessage extends ServiceMessage<Client> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final ClientGame backup;

    public GameBackupMessage(Game backup) throws GameException {
        this.backup = ClientGame.fromGame(backup);
    }

    public void execute(Client client) throws GameException {
        backup.setMyProperty(client.getMyUsername());
        client.applyGameBackup(backup);
    }

}