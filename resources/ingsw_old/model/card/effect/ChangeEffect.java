package it.polimi.ingsw_old.model.card.effect;

import it.polimi.ingsw_old.model.resource.ResourceType;

/**
 * Extension of Effect to specify change of value of white marbles in marbles market.
 */
public class ChangeEffect extends Effect{

    private final ResourceType resourceType;

    /**
     * Create a ChangeEffect object.
     * @param resourceType the resource type ti assign as new value to the white marbles.
     */
    public ChangeEffect(ResourceType resourceType) {
        this.effectType = EffectType.CHANGE;
        this.resourceType = resourceType;
    }

    /**
     *
     * @return the resource type assigned as new value.
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public EffectType getEffectType() {
        return super.getEffectType();
    }

    @Override
    public String toString(){
        return "Effect type: CHANGE\n" +
                "\tAny white marble become a " + resourceType.name();
    }
}
