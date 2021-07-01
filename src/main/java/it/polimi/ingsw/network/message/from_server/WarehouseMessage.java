package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.resource.ResourceDepot;

import java.io.Serializable;
import java.util.ArrayList;

public class WarehouseMessage extends Message<ClientController> implements Serializable {

    private static final long serialVersionUID = 10L;
    private final ArrayList<ResourceDepot> resourceDepots;

    public WarehouseMessage(ArrayList<ResourceDepot> resourceDepots, String playerUsername) {
        this.resourceDepots = resourceDepots;
        super.setPlayerUsername(playerUsername);
    }

    /**
     * Update warehouse on client
     * @param target
     */
    public void execute(ClientController target) {
        target.refreshWarehouse(resourceDepots, super.getPlayerUsername());
    }
}
