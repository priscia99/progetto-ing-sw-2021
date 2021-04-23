package it.polimi.ingsw.client;

import it.polimi.ingsw.model.resource.ResourceStock;

import java.util.ArrayList;

public class ClientStrongbox {

    private final ArrayList<ResourceStock> resourceStocks;

    public ClientStrongbox(ArrayList<ResourceStock> resourcePiles) {
        this.resourceStocks = resourcePiles;
    }

    public ResourceStock gerResourceStock(int index) {
        return resourceStocks.get(index);
    }
}
