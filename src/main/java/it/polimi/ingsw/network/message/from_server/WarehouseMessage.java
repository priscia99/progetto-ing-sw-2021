package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.resource.ResourceDepot;

import java.io.Serializable;
import java.util.ArrayList;

public class WarehouseMessage implements Message<ClientController>, Serializable {

    private static final long serialVersionUID = 1L;
    private final ArrayList<ResourceDepot> resourceDepots;

    public WarehouseMessage(ArrayList<ResourceDepot> resourceDepots) {
        this.resourceDepots = resourceDepots;
    }

    public void execute(ClientController target) {

    }
}
