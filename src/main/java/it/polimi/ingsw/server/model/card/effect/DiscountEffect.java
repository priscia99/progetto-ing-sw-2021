package it.polimi.ingsw.server.model.card.effect;

import it.polimi.ingsw.server.model.resource.ResourceType;

/**
 * Extension of Effect to specify discounts of the card market.
 */
public class DiscountEffect extends Effect {

    private final ResourceType resourceType;

    /**
     * Create a DiscountEffect object.
     * @param resourceType the resource type to be discounted.
     */
    public DiscountEffect(ResourceType resourceType) {
        this.effectType = EffectType.DISCOUNT;
        this.resourceType = resourceType;
    }

    /**
     *
     * @return the discounted resource
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
        return "Effect type: DISCOUNT\n" +
                "\tSpecial discount for resource " + resourceType.name();
    }
}
