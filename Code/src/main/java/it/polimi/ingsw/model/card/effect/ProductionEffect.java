package it.polimi.ingsw.model.card.effect;

import it.polimi.ingsw.model.resource.ResourcePile;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductionEffect extends Effect{

    private final List<ResourcePile> inPiles;
    private final List<ResourcePile> outPiles;

    public ProductionEffect(List<ResourcePile> inPiles, List<ResourcePile> outPiles) {
        this.effectType = EffectType.PRODUCTION;
        this.inPiles = inPiles;
        this.outPiles = outPiles;
    }

    static public ProductionEffect getBasicProduction(){
        List<ResourcePile> inResources = new ArrayList<>(Arrays.asList(new ResourcePile(2, ResourceType.GENERIC)));
        List<ResourcePile> outResources = new ArrayList<>(Arrays.asList(new ResourcePile(1, ResourceType.GENERIC)));
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
}
