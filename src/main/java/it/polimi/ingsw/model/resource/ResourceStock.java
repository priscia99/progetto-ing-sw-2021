package it.polimi.ingsw.model.resource;

import it.polimi.ingsw.exceptions.IllegalResourceException;

public class ResourceStock {

    protected ResourceType resourceType;
    private int quantity;

    public ResourceStock(ResourceType resourceType) {
        this.resourceType = resourceType;
        this.quantity = 0;
    }

    public ResourceStock(ResourceType resourceType, int quantity) {
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isEmpty() {
        return this.quantity == 0;
    }

    public void incrementResource(ResourceType resourceType) {
        if (!resourceType.equals(this.resourceType)) {
            throw new IllegalResourceException();
        }
        this.quantity++;
    }

    public void decrementResource(ResourceType resourceType) {
        if (!resourceType.equals(this.resourceType)) {
            throw new IllegalResourceException();
        } else if (this.quantity == 0) {
            throw new UnsupportedOperationException("Stock is already empty");
        }
        this.quantity--;
    }

    public boolean contains(ResourceType resourceType) {
        return this.resourceType.equals(resourceType);
    }
}
