package it.polimi.ingsw.model.card.effect;

import it.polimi.ingsw.model.resource.ResourceType;

public class DiscountEffect extends Effect {

    private final ResourceType resourceType;

    public DiscountEffect(EffectType effectType, ResourceType resourceType) {
        super(effectType);
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
