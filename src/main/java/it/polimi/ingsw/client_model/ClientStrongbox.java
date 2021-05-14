package it.polimi.ingsw.client_model;

import it.polimi.ingsw.server_model.player_board.storage.Strongbox;
import it.polimi.ingsw.server_model.resource.ResourceStock;

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
