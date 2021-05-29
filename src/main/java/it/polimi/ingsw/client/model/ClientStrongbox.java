package it.polimi.ingsw.client.model;

import java.util.*;
import it.polimi.ingsw.server.model.resource.*;
import it.polimi.ingsw.server.model.player_board.storage.*;

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
