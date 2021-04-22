package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.model.resource.ResourceDepot;
import it.polimi.ingsw.model.resource.ResourceStock;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Storage extends Observable<ArrayList<ResourceStock>> {

    protected ArrayList<ResourceStock> resourceStocks;

    public Storage() {
        this.resourceStocks = new ArrayList<>();
    }

    public ResourceStock getResourceStock(int index) {
        return resourceStocks.get(index);
    }

    public boolean isEmpty() {
        return this.resourceStocks.stream().allMatch(ResourceStock::isEmpty);
    }

    public abstract int countByResourceType(ResourceType resourceType);
}
