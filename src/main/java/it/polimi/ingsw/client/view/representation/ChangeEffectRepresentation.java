package it.polimi.ingsw.client.view.representation;

import it.polimi.ingsw.server.model.resource.ResourceType;

public class ChangeEffectRepresentation extends Representation {

    private final ResourceType resourceType;

    public ChangeEffectRepresentation(String assetId, ResourceType resourceType) {
        super(assetId, "change_effect");
        this.resourceType = resourceType;
    }


    @Override
    public String getCLIRender() {
        return String.format(format, resourceType.toString());
    }
}

