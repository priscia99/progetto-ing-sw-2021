package it.polimi.ingsw.server.model.card.color;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The ColorPile wraps a color value and add a quantity to it.
 */
public class ColorPile implements Serializable {

    private static final long serialVersionUID = 8L;
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

    /**
     * Static method to convert ColorPiles into String representation.
     * @param piles ColorPiles to convert
     * @return a String representing the input piles
     */
    public static String pilesToString(ArrayList<ColorPile> piles) {
        StringBuilder render = new StringBuilder();
        piles.forEach(
                pile -> {
                    int quantity = pile.getQuantity();
                    String color = pile.getColor().toString();
                    render.append(quantity);
                    render.append(" ");
                    render.append(color);
                    render.append(" ");
                }
        );
        render.delete(render.length()-1, render.length());
        return render.toString();
    }
}
