package it.polimi.ingsw.server.model.resource;



import java.io.Serializable;

/**
 * Class that models the depots of resources.
 */
public class ResourceDepot extends ResourceStock implements Serializable{

    private static final long serialVersionUID = 1011L;
    private int capacity;

    /**
     * Create a ResourceDepot object with the given ResourceType and given capacity.
     * @param resourceType the accepted type of resources
     * @param capacity the max quantity of resources that the depot can contains
     */
    public ResourceDepot(ResourceType resourceType, int capacity) {
        super(resourceType);
        this.capacity = capacity;
    }

    /**
     * Create a ResourceDepot object with the given ResourceType, given capacity and a give quantity of resources already contained in it.
     * @param resourceType the accepted type of resources
     * @param capacity the max quantity of resources that the depot can contains
     * @param quantity the quantity of resources contained in the depot
     */
    public ResourceDepot(ResourceType resourceType, int quantity, int capacity) {
        super(resourceType, quantity);
        this.capacity = capacity;
    }

    /**
     * Create a ResourceDepot object with only a give ResourceType.
     * @param resourceType the accepted type of resources
     */
    public ResourceDepot(ResourceType resourceType) {
        super(resourceType);
        this.capacity = 0;
    }

    /**
     * Create a ResourceDepot object with only a give ResourceType.
     * @param capacity the max quantity of resources that the depot can contains
     */
    public ResourceDepot(int capacity) {
        super(ResourceType.BLANK);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    /**
     * Test if the depot is full.
     * @return true if the depot is full (quantity equals to capacity); else false.
     */
    public boolean isFull() {
        return this.getQuantity() == this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * Increment quantity of resources stored in depot
     * @param resourceType the type of the new resource
     * @throws Exception
     */
    @Override
    public void incrementResource(ResourceType resourceType) throws Exception {
        if (this.isFull()) {
            throw new Exception("Depot is full");
        }
        super.incrementResource(resourceType);
    }
}
