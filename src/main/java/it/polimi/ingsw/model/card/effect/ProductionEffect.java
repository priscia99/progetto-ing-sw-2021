package it.polimi.ingsw.model.card.effect;

import it.polimi.ingsw.model.resource.ResourcePile;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Extension of Effect to specify productions.
 */
public class ProductionEffect extends Effect{

    private final ArrayList<ResourcePile> inPiles;
    private final ArrayList<ResourcePile> outPiles;

    /**
     * Create a ProductionEffect object.
     * @param inPiles the piles of resources consumed by the production
     * @param outPiles the piles of resources returned by the production
     */
    public ProductionEffect(ArrayList<ResourcePile> inPiles, ArrayList<ResourcePile> outPiles) {
        this.effectType = EffectType.PRODUCTION;
        this.inPiles = inPiles;
        this.outPiles = outPiles;
    }

    /**
     *
     * @return a basic production effect that takes two resources of your choice and returns one resource of
     * your choice (only consumable resources)
     */
    static public ProductionEffect getBasicProduction() {
        ArrayList<ResourcePile> inResources = new ArrayList<>(Arrays.asList(
                new ResourcePile(1, ResourceType.GENERIC),
                new ResourcePile(1, ResourceType.GENERIC)));
        ArrayList<ResourcePile> outResources = new ArrayList<>(Arrays.asList(new ResourcePile(1, ResourceType.GENERIC)));
        return new ProductionEffect(inResources, outResources);
    }

    /**
     *
     * @return the input piles of resources
     */
    public List<ResourcePile> getInPiles() {
        return inPiles;
    }

    /**
     *
     * @return the output piles of resoruces
     */
    public List<ResourcePile> getOutPiles() {
        return outPiles;
    }

    @Override
    public EffectType getEffectType() {
        return super.getEffectType();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Effect type: PRODUCTION\n");
        buffer.append("\tRequired resources: \n");
        for(ResourcePile pile : inPiles){
            buffer.append("\t\tType: ").append(pile.getResourceType().name());
            buffer.append(" - Quantity: ").append(pile.getQuantity()).append("\n");
        }
        buffer.append("\tOutput resources: \n");
        for(ResourcePile pile : outPiles){
            buffer.append("\t\tType: ").append(pile.getResourceType().name());
            buffer.append(" - Quantity: ").append(pile.getQuantity()).append("\n");
        }
        return buffer.toString();
    }
}
