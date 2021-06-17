package it.polimi.ingsw.server.model.resource;

import it.polimi.ingsw.exceptions.IllegalResourceException;

import java.io.Serializable;
import java.util.ArrayList;

public class ResourceStock implements Serializable {

    private static final long serialVersionUID = 1L;
    protected ResourceType resourceType;
    private int quantity;

    public ResourceStock(ResourceType resourceType) {
        this.resourceType = resourceType;
        this.quantity = 0;
    }

    public ResourceStock(ResourceType resourceType, int quantity) {
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isEmpty() {
        return this.quantity == 0;
    }

    public void incrementResource(ResourceType resourceType) throws IllegalResourceException{
        if (!resourceType.equals(this.resourceType)) {
            throw new IllegalResourceException("You cannot have different resource types in the same depot");
        }
        this.quantity++;
    }

    public void incrementResource(ResourceType resourceType, int quantity){
        if (!resourceType.equals(this.resourceType)) {
            throw new IllegalResourceException();
        }
        this.quantity+=quantity;
    }

    public void decrementResource(ResourceType resourceType) {
        if (!resourceType.equals(this.resourceType)) {
            throw new IllegalResourceException();
        } else if (this.quantity == 0) {
            throw new UnsupportedOperationException("Stock is already empty");
        }
        this.quantity--;
    }

    public boolean contains(ResourceType resourceType) {
        return this.resourceType.equals(resourceType);
    }

    public static String stocksToString(ArrayList<ResourceStock> stocks) {
        StringBuilder render = new StringBuilder();
        stocks.forEach(
                resourceStock -> {
                    int quantity = resourceStock.getQuantity();
                    String type = resourceStock.getResourceType().toString();
                    render.append(quantity);
                    render.append(" ");
                    render.append(type);
                    render.append(" ");
                }
        );
        render.delete(render.length()-1, render.length());
        return render.toString();
    }
}
