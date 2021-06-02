package it.polimi.ingsw.network.message.from_server;


import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.resource.ResourceStock;

import java.io.Serializable;
import java.util.ArrayList;

public class StrongboxMessage extends Message<ClientController> implements Serializable{

    private static final long serialVersionUID = 1L;
    private final ArrayList<ResourceStock> resourceStocks;

    public StrongboxMessage(ArrayList<ResourceStock> resourceStocks) {
        this.resourceStocks = resourceStocks;
    }

    public void execute(ClientController target) {

    }
}
