package v2.server.model.marble;

import v2.server.model.resource.ResourceType;

public class Marble {

    private final ResourceType resourceType;

    public Marble(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
}
