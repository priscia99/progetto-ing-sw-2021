package it.polimi.ingsw.network.message.from_client;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.resource.ConsumeTarget;
import it.polimi.ingsw.server.model.resource.ResourcePosition;

import java.util.ArrayList;

public class RemoveResourceMessage extends Message<ServerController> {

    private static final long serialVersionUID = 49L;
    ResourcePosition removeFrom;

    public RemoveResourceMessage(ResourcePosition removeFrom) {
        this.removeFrom = removeFrom;
    }

    /**
     * Execute remove resource action on server
     * @param target
     * @throws Exception
     */
    public void execute(ServerController target) throws Exception {
        target.removeResource(removeFrom);
    }
}
