package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.model.resource.ConsumableResource;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.List;

public abstract class Storage {

    private final List<ConsumableResource> consumableResources;

    public Storage(List<ConsumableResource> consumableResources) {
        this.consumableResources = consumableResources;
    }

    public List<ConsumableResource> getConsumableResources() {
        return consumableResources;
    }

    public boolean isEmpty() {
        return false;
    }

    public abstract void addResource(ResourceType resourceType);

    public abstract void removeResource();
}
