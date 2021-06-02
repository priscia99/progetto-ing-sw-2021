package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serializable;

public class GameReadyMessage implements Message<ClientController>, Serializable {

    private static final long serialVersionUID = 1L;

    private boolean isReady;

    public GameReadyMessage(boolean ready){
        this.isReady = ready;
    }

    @Override
    public void execute(ClientController target) {

    }
}