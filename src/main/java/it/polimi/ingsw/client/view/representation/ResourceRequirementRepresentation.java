package it.polimi.ingsw.client.view.representation;

import it.polimi.ingsw.server.model.resource.ResourceStock;

import java.util.ArrayList;
import java.util.List;

public class ResourceRequirementRepresentation extends Representation{

    private final List<ResourceStock> resourceStocks;

    public ResourceRequirementRepresentation(String assetId, List<ResourceStock> resourceStocks) {
        super(assetId, "resource_requirement");
        this.resourceStocks = resourceStocks;
    }

    @Override
    public String getCLIRender() {
        return String.format(format, ResourceStock.stocksToString(new ArrayList<>(resourceStocks)));
    }
}
