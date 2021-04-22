package it.polimi.ingsw.client;

import java.util.ArrayList;

public class ClientWarehouse {

    private final ArrayList<ResourcePile> resourcePiles;

    public ClientWarehouse(ArrayList<ResourcePile> resourcePiles) {
        this.resourcePiles = resourcePiles;
    }

    public ResourcePile getResourcePile(int index) {
        return resourcePiles.get(index);
    }
}
