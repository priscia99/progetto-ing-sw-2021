package it.polimi.ingsw.model.resource;

/**
 * Class that models consumable resources.
 */
public class ConsumableResource {

    private final ResourceType resourceType;
    private boolean toConsume;

    /**
     * Create ConsumableResource object of a given resource type.
     * @param resourceType the resource type of the consumable resource
     */
    public ConsumableResource(ResourceType resourceType) {
        this.resourceType = resourceType;
        this.toConsume = false;
    }

    /**
     *
     * @return the resource type of the consumable resource.
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * Test if the resource must be consumed (removed from a storage if it is in it)
     * @return true if the resource is to be consumed, false if not.
     */
    public boolean isToConsume() {
        return toConsume;
    }

    /**
     * Set or unset the resource to be consumed.
     * @param toConsume true to set the resource, false to unset
     */
    public void setToConsume(boolean toConsume) {
        this.toConsume = toConsume;
    }

    /**
     *
     * @return a copy as new object of the current instance.
     */
    public ConsumableResource copy() {
        ConsumableResource result = new ConsumableResource(this.resourceType);
        result.setToConsume(this.toConsume);
        return result;
    }
}
