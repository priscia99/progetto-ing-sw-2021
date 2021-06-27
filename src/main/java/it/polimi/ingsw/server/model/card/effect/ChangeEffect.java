package it.polimi.ingsw.server.model.card.effect;

import it.polimi.ingsw.server.model.resource.ResourceType;

import java.io.Serializable;

/**
 * Extension of Effect to specify change of value of white marbles in marbles market.
 */
public class ChangeEffect implements Effect, Serializable {

    private static final long serialVersionUID = 1L;
    private final ResourceType resourceType;
    private static final String CHANGE_EFFECT_FORMAT = "WHITE MARBLE --> [%s]";
    private EffectType effectType;
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

    public EffectType getEffectType() {
        return effectType;
    }

    @Override
    public String toString(){
        return String.format(CHANGE_EFFECT_FORMAT, resourceType.toString());
    }
}
