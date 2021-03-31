package it.polimi.ingsw.model;

public class Requirement {

    private final Object element;
    private final int quantity;

    public Requirement(Object element, int quantity) {
        this.element = element;
        this.quantity = quantity;
    }

    public Object getElement() {
        return element;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isFulfilled() {
        return true;
    }
}
