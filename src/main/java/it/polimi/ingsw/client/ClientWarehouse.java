package it.polimi.ingsw.client;

import it.polimi.ingsw.model.resource.ResourceDepot;

import java.util.ArrayList;

public class ClientWarehouse {

    private final ArrayList<ResourceDepot> resourceDepots;

    public ClientWarehouse(ArrayList<ResourceDepot> resourcePiles) {
        this.resourceDepots = resourcePiles;
    }

    public ResourceDepot getResourcePile(int index) {
        return resourceDepots.get(index);
    }
}
