package it.polimi.ingsw.model.resource;

public class ResourcePile {

    private int quantity;
    private ResourceType resourceType;

    public ResourcePile(int quantity, ResourceType resourceType) {
        this.quantity = quantity;
        this.resourceType = resourceType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void decrementQuantity() {
        this.quantity--;
    }
}
