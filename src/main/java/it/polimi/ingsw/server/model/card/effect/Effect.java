package it.polimi.ingsw.server.model.card.effect;

import it.polimi.ingsw.client.view.representation.Representation;

/**
 * Abstract class for effects.
 */
public abstract class Effect {

    protected EffectType effectType;

    /**
     *
     * @return the effect type
     */
    public EffectType getEffectType() {
        return effectType;
    }

    public abstract Representation toRepresentation();
}
