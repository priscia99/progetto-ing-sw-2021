package it.polimi.ingsw.model.card.color;

public class ColorPile {

    private final Color color;
    private final int quantity;

    public ColorPile(Color color, int quantity) {
        this.color = color;
        this.quantity = quantity;
    }

    public Color getColor() {
        return color;
    }

    public int getQuantity() {
        return quantity;
    }
}
