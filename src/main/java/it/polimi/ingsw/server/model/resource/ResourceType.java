package it.polimi.ingsw.server.model.resource;

/**
 * Enumeration for resource types.
 * Types: COIN, STONE, SERVANT, SHIELD, FAITH, BLANK, GENERIC.
 */
public enum ResourceType {
    COIN("COIN"),
    STONE("STONE"),
    SERVANT("SERVANT"),
    SHIELD("SHIELD"),
    FAITH("FAITH"),
    GENERIC("GENERIC"),
    BLANK("BLANK");


    private final String literal;

    ResourceType(String literal) {
        this.literal = literal;
    }

    @Override
    public String toString() {
        return literal;
    }
}
