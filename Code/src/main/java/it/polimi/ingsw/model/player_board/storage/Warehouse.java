package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.model.resource.ResourceType;

public class Warehouse {

    private final Depot[] depots;

    public Warehouse(Depot[] depots) {
        this.depots = depots;
    }

    public Depot[] getDepots() {
        return depots;
    }

    public boolean isEmpty() {
        return false;
    }

    public void addToDepot(int depotIndex, ResourceType resourceType) {

    }

    public void removeFromDepot(int depotIndex) {

    }
}
