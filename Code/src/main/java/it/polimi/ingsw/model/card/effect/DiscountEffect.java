package it.polimi.ingsw.model.card.effect;

import it.polimi.ingsw.model.resource.ResourceType;

public class DiscountEffect extends Effect {

    private final ResourceType resourceType;

    public DiscountEffect(ResourceType resourceType) {
        this.effectType = EffectType.DISCOUNT;
        this.resourceType = resourceType;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public EffectType getEffectType() {
        return super.getEffectType();
    }
}
