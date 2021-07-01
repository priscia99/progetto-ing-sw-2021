package it.polimi.ingsw.server.model.card.effect;

import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Extension of Effect to specify productions.
 */
public class ProductionEffect implements Effect, Serializable {

    private static final long serialVersionUID = 31L;
    private final ArrayList<ResourceStock> inStocks;
    private final ArrayList<ResourceStock> outStocks;
    private static final String PRODUCTION_EFFECT_FORMAT = "[%s] ==> [%s]";
    private EffectType effectType;
    /**
     * Create a ProductionEffect object.
     * @param inStocks the piles of resources consumed by the production
     * @param outStocks the piles of resources returned by the production
     */
    public ProductionEffect(ArrayList<ResourceStock> inStocks, ArrayList<ResourceStock> outStocks) {
        this.effectType = EffectType.PRODUCTION;
        this.inStocks = inStocks;
        this.outStocks = outStocks;
    }

    /**
     *
     * @return a basic production effect that takes two resources of your choice and returns one resource of
     * your choice (only consumable resources)
     */
    static public ProductionEffect getBasicProduction() {
        ArrayList<ResourceStock> inResources = new ArrayList<>(Arrays.asList(
                new ResourceStock(ResourceType.GENERIC, 1),
                new ResourceStock(ResourceType.GENERIC, 1)));
        ArrayList<ResourceStock> outResources = new ArrayList<>(Collections.singletonList(new ResourceStock(ResourceType.GENERIC, 1)));
        return new ProductionEffect(inResources, outResources);
    }

    /**
     *
     * @return the input piles of resources
     */
    public ArrayList<ResourceStock> getInStocks() {
        return inStocks;
    }

    /**
     *
     * @return the output piles of resoruces
     */
    public List<ResourceStock> getOutStocks() {
        return outStocks;
    }

    public ArrayList<ResourceStock> getOutStockConverted(ResourceType converted){
        ArrayList<ResourceStock> toReturn = new ArrayList<>();
        for(ResourceStock stock : outStocks){
            if(stock.getResourceType().equals(ResourceType.GENERIC)){
                toReturn.add(new ResourceStock(converted, stock.getQuantity()));
            } else {
                toReturn.add(stock);
            }
        }
        return toReturn;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    @Override
    public String toString() {
        String inString = ResourceStock.stocksToString(inStocks);
        String outString = ResourceStock.stocksToString(outStocks);
        return String.format(PRODUCTION_EFFECT_FORMAT, inString, outString);
    }
}
