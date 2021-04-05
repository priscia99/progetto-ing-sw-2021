package it.polimi.ingsw.model.card.effect;

import it.polimi.ingsw.model.resource.ResourceType;

public class ChangeEffect extends Effect{

    private final ResourceType resourceType;

    public ChangeEffect(ResourceType resourceType) {
        this.effectType = EffectType.CHANGE;
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
