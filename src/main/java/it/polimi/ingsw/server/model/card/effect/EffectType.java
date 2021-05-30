package it.polimi.ingsw.server.model.card.effect;

/**
 * Enumeration for effects types: PRODUCTION, CHANGE, DEPOT, DISCOUNT.
 */
public enum EffectType {
    PRODUCTION("PRODUCTION"),
    CHANGE("CHANGE"),
    DEPOT("DEPOT"),
    DISCOUNT("DISCOUNT");

    private final String literal;

    EffectType(String literal) {
        this.literal = literal;
    }

    public String toString() {
        return literal;
    }
}
