package it.polimi.ingsw.server.model.card.color;

/**
 * Enumeration for color type. Possible values: PURPLE, YELLOW, GREEN, BLUE.
 */
public enum Color {
    PURPLE("PURPLE"),
    YELLOW("YELLOW"),
    GREEN("GREEN"),
    BLUE("BLUE");

    private final String literal;

    Color(String literal) {
        this.literal = literal;
    }

    public String toString() {
        return literal;
    }
}
