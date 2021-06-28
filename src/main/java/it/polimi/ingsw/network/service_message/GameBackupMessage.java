package it.polimi.ingsw.network.service_message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.model.ClientGame;

import it.polimi.ingsw.network.service_message.ServiceMessage;
import it.polimi.ingsw.server.model.game.Game;

import java.io.Serializable;
import java.util.ArrayList;

public class GameBackupMessage extends ServiceMessage<Client> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final ClientGame backup;

    public GameBackupMessage(Game backup) throws Exception {
        this.backup = ClientGame.fromGame(backup);
    }

    public void execute(Client client) throws Exception {
        client.applyGameBackup(backup);
    }

}