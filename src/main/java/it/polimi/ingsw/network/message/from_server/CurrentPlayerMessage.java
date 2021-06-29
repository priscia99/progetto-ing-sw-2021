package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serializable;

public class CurrentPlayerMessage extends Message<ClientController> implements Serializable {

    private static final long serialVersionUID = 3L;
    private String username;

    public CurrentPlayerMessage(String username) {
        this.username = username;
    }

    public void execute(ClientController target) {
        target.setCurrentPlayer(username);
    }
}
