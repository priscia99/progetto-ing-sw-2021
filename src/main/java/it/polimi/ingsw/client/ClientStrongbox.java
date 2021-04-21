package it.polimi.ingsw.client;

import it.polimi.ingsw.model.resource.ResourcePile;

import java.util.ArrayList;

public class ClientStrongbox {

    private ArrayList<ResourcePile> resourcePiles;

    public ClientStrongbox(ArrayList<ResourcePile> resourcePiles) {
        this.resourcePiles = resourcePiles;
    }

    public ResourcePile getResourcePile(int index) {
        return resourcePiles.get(index);
    }
}
