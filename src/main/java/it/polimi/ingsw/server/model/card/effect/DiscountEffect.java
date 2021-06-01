package it.polimi.ingsw.server.model.card.effect;

import it.polimi.ingsw.server.model.resource.ResourceType;

import java.io.Serializable;

/**
 * Extension of Effect to specify discounts of the card market.
 */
public class DiscountEffect extends Effect implements Serializable {

    private static final long serialVersionUID = 1L;
    private final ResourceType resourceType;
    private static final String DISCOUNT_EFFECT_FORMAT = "%s -1 REQUIRED";

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
        return String.format(DISCOUNT_EFFECT_FORMAT, resourceType.toString());
    }
}
