package it.polimi.ingsw.network.message.from_client;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductionMessage extends Message<ServerController> implements Serializable {

    private static final long serialVersionUID = 1L;
    HashMap<ResourcePosition, ResourceStock> consumedResources;
    ArrayList<ProductionEffect> productionsToActivate;

    public ProductionMessage(HashMap<ResourcePosition, ResourceStock> consumed,
                             ArrayList<ProductionEffect> productions) {
        consumedResources = consumed;
        productionsToActivate = productions;
    }

    public void execute(ServerController target) throws Exception {
        target.startProduction(consumedResources, productionsToActivate);
    }
}
