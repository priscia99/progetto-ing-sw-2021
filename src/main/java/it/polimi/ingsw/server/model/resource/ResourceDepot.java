package it.polimi.ingsw.server.model.resource;

import java.io.Serializable;

public class ResourceDepot extends ResourceStock {

    private int capacity;

    public ResourceDepot(ResourceType resourceType, int capacity) {
        super(resourceType);
        this.capacity = capacity;
    }

    public ResourceDepot(ResourceType resourceType, int quantity, int capacity) {
        super(resourceType, quantity);
        this.capacity = capacity;
    }

    public ResourceDepot(ResourceType resourceType) {
        super(resourceType);
        this.capacity = 0;
    }

    public ResourceDepot(int capacity) {
        super(ResourceType.BLANK);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isFull() {
        return this.getQuantity() == this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public void incrementResource(ResourceType resourceType) {
        if (this.isFull()) {
            throw new UnsupportedOperationException("Depot is full");
        }
        super.incrementResource(resourceType);
    }
}