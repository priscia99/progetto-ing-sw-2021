package it.polimi.ingsw.network.message.from_client;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class StartProductionAction implements Message<ServerController>, Serializable {

    private static final long serialVersionUID = 1L;
    HashMap<ResourcePosition, ResourceStock> consumedResources;
    ArrayList<ProductionEffect> productionsToActivate;

    public StartProductionAction(HashMap<ResourcePosition, ResourceStock> consumed,
                                 ArrayList<ProductionEffect> productions) {
        consumedResources = consumed;
        productionsToActivate = productions;
    }

    public void execute(ServerController target) {
        target.startProduction(consumedResources, productionsToActivate);
    }
}
