package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serializable;

public class ResourceReadyMessage extends Message<ClientController> implements Serializable{

    private static final long serialVersionUID = 32L;

    private boolean isResourceReady;

    public ResourceReadyMessage(boolean ready){
        this.isResourceReady = ready;
    }

    @Override
    public void execute(ClientController target) {

    }
}
