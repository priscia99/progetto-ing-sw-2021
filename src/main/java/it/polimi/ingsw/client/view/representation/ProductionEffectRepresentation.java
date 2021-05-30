package it.polimi.ingsw.client.view.representation;

import it.polimi.ingsw.server.model.resource.ResourceStock;

import java.util.ArrayList;

public class ProductionEffectRepresentation extends Representation{

    private final ArrayList<ResourceStock> inStocks;
    private final ArrayList<ResourceStock> outStocks;

    public ProductionEffectRepresentation(String assetId, ArrayList<ResourceStock> inStocks, ArrayList<ResourceStock> outStocks) {
        super(assetId, "production_effect");
        this.inStocks = inStocks;
        this.outStocks = outStocks;
    }

    @Override
    public String getCLIRender() {
        return String.format(format, ResourceStock.stocksToString(inStocks), ResourceStock.stocksToString(outStocks));
    }
}
