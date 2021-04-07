package it.polimi.ingsw.model.card.effect;

import it.polimi.ingsw.model.resource.ResourcePile;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductionEffect extends Effect{

    private final ArrayList<ResourcePile> inPiles;
    private final ArrayList<ResourcePile> outPiles;

    public ProductionEffect(ArrayList<ResourcePile> inPiles, ArrayList<ResourcePile> outPiles) {
        this.effectType = EffectType.PRODUCTION;
        this.inPiles = inPiles;
        this.outPiles = outPiles;
    }

    static public ProductionEffect getBasicProduction() {
        // FIXME: wait for builder
        ArrayList<ResourcePile> inResources = new ArrayList<>(Arrays.asList(new ResourcePile(2, ResourceType.GENERIC)));
        ArrayList<ResourcePile> outResources = new ArrayList<>(Arrays.asList(new ResourcePile(1, ResourceType.GENERIC)));
        return new ProductionEffect(inResources, outResources);
    }

    public List<ResourcePile> getInPiles() {
        return inPiles;
    }

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
