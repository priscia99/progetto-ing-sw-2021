package it.polimi.ingsw.network.message.from_client;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.io.Serializable;
import java.util.ArrayList;

public class PickResourcesMessage extends Message<ServerController> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final MarbleSelection marbleSelection;
    private final ArrayList<ResourcePosition> positions;
    ArrayList<ResourceType> conversions;

    public PickResourcesMessage(MarbleSelection marbleSelection, ArrayList<ResourcePosition> positions,  ArrayList<ResourceType> conversions) {
        this.marbleSelection = marbleSelection;
        this.positions = positions;
        this.conversions = conversions;
    }

    public void execute(ServerController target) throws Exception {
        target.pickResources(marbleSelection, positions, conversions);
    }
}