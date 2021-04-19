package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.model.resource.ConsumableResource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Storage extends Observable<ArrayList<ConsumableResource>> {

    protected ArrayList<ConsumableResource> consumableResources;

    public Storage() {
        this.consumableResources = new ArrayList<>();
    }

    public ArrayList<ConsumableResource> getConsumableResources() {
        return consumableResources;
    }

    public boolean isEmpty() {
        return (consumableResources == null || consumableResources.isEmpty());
    }

    public abstract void addResource(ResourceType resourceType);

    public void removeConsumedResources(){
        consumableResources =
                consumableResources.stream().filter(elem -> !elem.isToConsume()).collect(Collectors.toCollection(ArrayList::new));
    }

}
