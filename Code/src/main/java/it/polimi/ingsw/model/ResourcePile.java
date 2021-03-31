package it.polimi.ingsw.model;

public class ResourcePile {

    private int quantity;

    private Resource resource;

    public ResourcePile(int quantity, Resource resource) {
        this.quantity = quantity;
        this.resource = resource;
    }

    public int getQuantity() {
        return quantity;
    }

    public Resource getResource() {
        return resource;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
