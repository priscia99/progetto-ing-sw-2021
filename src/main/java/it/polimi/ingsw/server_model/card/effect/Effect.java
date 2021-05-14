package it.polimi.ingsw.server_model.card.effect;

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
}
