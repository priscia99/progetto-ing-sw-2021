package it.polimi.ingsw.client.view.representation;

import it.polimi.ingsw.server.model.resource.ResourceType;

public class DiscountEffectRepresentation extends Representation{

    private final ResourceType resourceType;

    public DiscountEffectRepresentation(String assetId, ResourceType resourceType) {
        super(assetId, "discount_effect");
        this.resourceType = resourceType;
    }

    @Override
    public String getCLIRender() {
        return String.format(format, resourceType.toString());
    }
}
