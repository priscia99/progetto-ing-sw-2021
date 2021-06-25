package it.polimi.ingsw.network.service_message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.server.Lobby;

import java.io.Serializable;

public class ClientReadyServiceMessage extends ServiceMessage<Lobby> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Override
    public void execute(Lobby lobby) {
        lobby.setupMVC();
    }
}
