package it.polimi.ingsw.network.message.from_server;


import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class FaithPathMessage extends Message<ClientController> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final int faithPoints;
    private final ArrayList<Boolean> popeFavors;

    public FaithPathMessage(int faithPoints, ArrayList<Boolean> popeFavors) {
        this.faithPoints = faithPoints;
        this.popeFavors = popeFavors;
    }

    public void execute(ClientController target) {

    }
}
