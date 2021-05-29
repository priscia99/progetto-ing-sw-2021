package it.polimi.ingsw.client.model;

import java.util.*;
import java.util.stream.Collectors;
import it.polimi.ingsw.server.model.resource.*;
import it.polimi.ingsw.server.model.player_board.storage.*;

public class ClientWarehouse {

    private ArrayList<ResourceDepot> resourceDepots;

    public ClientWarehouse() {
        this.resourceDepots = new ArrayList<>();
//        this.resourceDepots = warehouse.getResourceStocks()
//                .stream().map(resourceStock -> (ResourceDepot) resourceStock)
//                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void setResourceDepots(ArrayList<ResourceDepot> resourceDepots) {
        this.resourceDepots = resourceDepots;
    }

    public ClientWarehouse(ArrayList<ResourceDepot> resourcePiles) {
        this.resourceDepots = resourcePiles;
    }

    public ResourceDepot getResourceDepot(int index) {
        return resourceDepots.get(index);
    }
}
