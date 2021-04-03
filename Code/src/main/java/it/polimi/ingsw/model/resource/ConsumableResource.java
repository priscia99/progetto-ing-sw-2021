package it.polimi.ingsw.model.resource;

public class ConsumableResource {

    private final ResourceType resourceType;
    private boolean toConsume;

    public ConsumableResource(ResourceType resourceType) {
        this.resourceType = resourceType;
        this.toConsume = false;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public boolean isToConsume() {
        return toConsume;
    }

    public void setToConsume(boolean toConsume) {
        this.toConsume = toConsume;
    }
}
