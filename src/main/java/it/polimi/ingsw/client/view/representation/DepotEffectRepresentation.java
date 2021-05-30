package it.polimi.ingsw.client.view.representation;

import it.polimi.ingsw.server.model.resource.ResourceDepot;

public class DepotEffectRepresentation extends Representation{

    private final ResourceDepot resourceDepot;

    public DepotEffectRepresentation(String assetId, ResourceDepot resourceDepot) {
        super(assetId, "change_effect");
        this.resourceDepot = resourceDepot;
    }

    @Override
    public String getCLIRender() {
        return String.format(format, resourceDepot.getResourceType().toString());
    }
}
