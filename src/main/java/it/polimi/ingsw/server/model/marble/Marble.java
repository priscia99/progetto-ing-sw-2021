package it.polimi.ingsw.server.model.marble;

import it.polimi.ingsw.server.model.resource.ResourceType;

import java.io.Serializable;

/**
 * Class that models the marbles of resources.
 */
public class Marble implements Serializable {

    private static final long serialVersionUID = 1000L;
    private final ResourceType resourceType;

    /**
     * Create a Marble object with the given ResourceType.
     * @param resourceType the ResourceType to assign to the new marble
     */
    public Marble(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
}
