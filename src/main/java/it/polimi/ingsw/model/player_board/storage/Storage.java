package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.model.resource.ResourceStock;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.network.observer.ObservableWithOption;

import java.util.ArrayList;

public abstract class Storage extends ObservableWithOption<Storage> {

    protected ArrayList<ResourceStock> resourceStocks;

    public Storage() {
        this.resourceStocks = new ArrayList<>();
    }

    public ResourceStock getResourceStock(int index) {
        return resourceStocks.get(index);
    }

    public ArrayList<ResourceStock> getResourceStocks() {
        return resourceStocks;
    }

    public boolean isEmpty() {
        return this.resourceStocks.stream().allMatch(ResourceStock::isEmpty);
    }

    public abstract int countByResourceType(ResourceType resourceType);
}
