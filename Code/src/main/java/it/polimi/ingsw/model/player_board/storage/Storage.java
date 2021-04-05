package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.model.resource.ConsumableResource;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Storage {

    protected List<ConsumableResource> consumableResources;

    public Storage() {
        this.consumableResources = new ArrayList<>();
    }

    public List<ConsumableResource> getConsumableResources() {
        return consumableResources;
    }

    public boolean isEmpty() {
        return (consumableResources == null || consumableResources.isEmpty());
    }

    public abstract void addResource(ResourceType resourceType);

    public void removeConsumedResources(){
        consumableResources =
                consumableResources.stream().filter(elem -> !elem.isToConsume()).collect(Collectors.toList());
    }

}
