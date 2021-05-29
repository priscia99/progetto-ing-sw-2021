package it.polimi.ingsw.server.model.card.color;

import java.io.Serializable;

/**
 * The ColorPile wraps a color value and add a quantity to it.
 */
public class ColorPile implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Color color;
    private final int quantity;

    /**
     * ColorPile constructor
     * @param color color value
     * @param quantity quantity of the color
     */
    public ColorPile(Color color, int quantity) {
        this.color = color;
        this.quantity = quantity;
    }

    /**
     *
     * @return the wrapped color of a ColorPile
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @return the quantity of color
     */
    public int getQuantity() {
        return quantity;
    }
}
