package it.polimi.ingsw.server_model.marble;

import it.polimi.ingsw.server_model.resource.ResourceType;

public class Marble {

    private final ResourceType resourceType;

    public Marble(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
}
