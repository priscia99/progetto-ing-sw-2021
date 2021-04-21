package it.polimi.ingsw.model.resource;

/**
 * Class that models a list of resources (types and quantities)
 */
public class ResourcePile {

    private int quantity;
    private ResourceType resourceType;

    /**
     * Create a ResourcePile object with given type and quantity.
     * @param quantity the quantity of resource
     * @param resourceType the type of resource
     */
    public ResourcePile(int quantity, ResourceType resourceType) {
        this.quantity = quantity;
        this.resourceType = resourceType;
    }

    /**
     *
     * @return the quantity of resource.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Update the quantity of resource.
     * @param quantity the new quantity of resource.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return the type of resource.
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * Update the type of resource.
     * @param resourceType the new type of resource
     */
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * Increment by 1 the quantity of resource.
     */
    public void incrementQuantity() {
        this.quantity++;
    }

    /**
     * Decrement by 1 the quantity fo resource.
     * @throws UnsupportedOperationException if the quantity is already 0
     */
    public void decrementQuantity() {
        if (this.quantity == 0) {
            throw new UnsupportedOperationException("Quantity can not be negative.");
        }
        this.quantity--;
    }
}
