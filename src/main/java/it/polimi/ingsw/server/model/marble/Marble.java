package it.polimi.ingsw.server.model.marble;

import it.polimi.ingsw.server.model.resource.ResourceType;

public class Marble {

    private final ResourceType resourceType;

    public Marble(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
}
