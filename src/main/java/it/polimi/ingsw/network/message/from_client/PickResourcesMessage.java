package it.polimi.ingsw.network.message.from_client;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.resource.ResourcePosition;

import java.io.Serializable;
import java.util.ArrayList;

public class PickResourcesMessage implements Message<ServerController>, Serializable {

    private static final long serialVersionUID = 1L;
    private final MarbleSelection marbleSelection;
    private final ArrayList<ResourcePosition> positions;

    public PickResourcesMessage(MarbleSelection marbleSelection, ArrayList<ResourcePosition> positions) {
        this.marbleSelection = marbleSelection;
        this.positions = positions;
    }

    public void execute(ServerController target) {
        target.pickResources(marbleSelection, positions);
    }
}