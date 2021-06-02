package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serializable;

public class LeadersReadyMessage extends Message<ClientController> implements Serializable{

    private static final long serialVersionUID = 1L;

    private boolean isLeaderReady;

    public LeadersReadyMessage(boolean ready){
        this.isLeaderReady = ready;
    }

    @Override
    public void execute(ClientController target) {

    }
}
