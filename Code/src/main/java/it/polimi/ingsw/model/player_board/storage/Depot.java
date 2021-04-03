package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.model.resource.ConsumableResource;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.List;

public class Depot extends Storage{

    private int capacity;

    public Depot(List<ConsumableResource> consumableResources, int capacity) {
        super(consumableResources);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public Depot(List<ConsumableResource> consumableResources) {
        super(consumableResources);
    }

    @Override
    public List<ConsumableResource> getConsumableResources() {
        return super.getConsumableResources();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public void addResource(ResourceType resourceType) {

    }

    @Override
    public void removeResource() {

    }

    public void getResourceType() {

    }
}
