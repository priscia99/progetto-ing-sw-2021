package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.player_board.storage.Strongbox;
import it.polimi.ingsw.model.resource.ResourceStock;

import java.util.ArrayList;

public class ClientStrongbox {

    private final ArrayList<ResourceStock> resourceStocks;

    public ClientStrongbox(Strongbox strongbox) {
        this.resourceStocks = strongbox.getResourceStocks();
    }

    public ClientStrongbox(ArrayList<ResourceStock> resourcePiles) {
        this.resourceStocks = resourcePiles;
    }

    public ResourceStock gerResourceStock(int index) {
        return resourceStocks.get(index);
    }
}
