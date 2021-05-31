package it.polimi.ingsw.server.model.card.effect;

import it.polimi.ingsw.client.view.representation.Representation;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.io.Serializable;

/**
 * Extension of Effect to specify discounts of the card market.
 */
public class DiscountEffect extends Effect implements Serializable {

    private static final long serialVersionUID = 1L;
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

    public Representation toRepresentation(){
        // TODO fill toRepresentation method
        return null;
    }
}
