package it.polimi.ingsw.model.card.effect;

public abstract class Effect {

    private final EffectType effectType;

    public Effect(EffectType effectType) {
        this.effectType = effectType;
    }

    public EffectType getEffectType() {
        return effectType;
    }
}
