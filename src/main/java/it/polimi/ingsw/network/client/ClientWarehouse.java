package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.player_board.storage.Warehouse;
import it.polimi.ingsw.model.resource.ResourceDepot;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ClientWarehouse {

    private final ArrayList<ResourceDepot> resourceDepots;

    public ClientWarehouse(Warehouse warehouse) {
        this.resourceDepots = warehouse.getResourceStocks()
                .stream().map(resourceStock -> (ResourceDepot) resourceStock)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ClientWarehouse(ArrayList<ResourceDepot> resourcePiles) {
        this.resourceDepots = resourcePiles;
    }

    public ResourceDepot getResourceDepot(int index) {
        return resourceDepots.get(index);
    }
}