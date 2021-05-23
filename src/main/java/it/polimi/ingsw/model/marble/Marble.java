package it.polimi.ingsw.model.marble;

import it.polimi.ingsw.model.resource.ResourceType;

public class Marble {

    private final ResourceType resourceType;

    public Marble(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
}
