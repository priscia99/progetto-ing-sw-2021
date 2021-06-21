package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class BlackCrossMessage extends Message<ClientController> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final int position;
    private final ArrayList<Boolean> popeFavors;

    public BlackCrossMessage(int position, ArrayList<Boolean> popeFavors){
        this.popeFavors = popeFavors;
        this.position = position;
    }

    @Override
    public void execute(ClientController target) {
        target.refreshFaithPath(popeFavors, position);
    }
}