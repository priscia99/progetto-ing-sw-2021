package it.polimi.ingsw.model.card.effect;

import it.polimi.ingsw.model.resource.ResourcePile;

import java.util.List;

public class ProductionEffect extends Effect{

    private final List<ResourcePile> inPiles;
    private final List<ResourcePile> outPiles;

    public ProductionEffect(EffectType effectType, List<ResourcePile> inPiles, List<ResourcePile> outPiles) {
        super(effectType);
        this.inPiles = inPiles;
        this.outPiles = outPiles;
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
