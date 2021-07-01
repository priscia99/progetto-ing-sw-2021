package it.polimi.ingsw.network.service_message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.server.Lobby;

import java.io.Serializable;

public class ClientReadyServiceMessage extends ServiceMessage<Lobby> implements Serializable {

    private static final long serialVersionUID = 47L;

    /**
     * Setup observers on lobby
     * @param lobby
     */
    @Override
    public void execute(Lobby lobby) {
        lobby.setupMVC();
    }
}
