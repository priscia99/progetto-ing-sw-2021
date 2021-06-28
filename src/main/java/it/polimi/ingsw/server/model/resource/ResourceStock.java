package it.polimi.ingsw.server.model.resource;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that models the stocks of resources.
 */
public class ResourceStock implements Serializable {

    private static final long serialVersionUID = 1L;
    protected ResourceType resourceType;
    private int quantity;

    /**
     * Create an empty stock of a given ResourceType.
     * @param resourceType the type of resources contained in the stock
     */
    public ResourceStock(ResourceType resourceType) {
        this.resourceType = resourceType;
        this.quantity = 0;
    }

    /**
     * Create a resource stock of a given ResourceType and a given quantity of them.
     * @param resourceType the type of resources contained in the stock
     * @param quantity the quantity of the contained resources
     */
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

    /**
     * Test if the stock is empty or not.
     * @return true if the of resources is 0; else false
     */
    public boolean isEmpty() {
        return this.quantity == 0;
    }

    /**
     * Increment by one the quantity of resources contained in the stock (only if the ResourceType of the new stock correspond to the one already contained).
     * @param resourceType the type of the new resource
     * @throws Exception
     */
    public void incrementResource(ResourceType resourceType) throws Exception{
        if (!resourceType.equals(this.resourceType)) {
            throw new Exception("You cannot have different resource types in the same depot");
        }
        this.quantity++;
    }

    /**
     * Increment by a quantity the quantity of resources contained in the stock (only if the ResourceType of the new stock correspond to the one already contained).
     * @param resourceType the type of the new resource
     * @param quantity the quantity of resources to add
     * @throws Exception
     */
    public void incrementResource(ResourceType resourceType, int quantity) throws Exception {
        if (!resourceType.equals(this.resourceType)) {
            throw new Exception("Trying to add different kind of resource!");
        }
        this.quantity+=quantity;
    }

    /**
     * Decrement by one the quantity of resources contained in the stock (only if the ResourceType of the new stock correspond to the one already contained).
     * @param resourceType the type of the new resource
     * @throws Exception
     */
    public void decrementResource(ResourceType resourceType) throws Exception {
        if (!resourceType.equals(this.resourceType)) {
            throw new Exception("Trying to decrement differend kind of resource!");
        } else if (this.quantity == 0) {
            throw new Exception("Stock is already empty");
        }
        this.quantity--;
    }

    /**
     * Test if the stock contains or not a give ResourceType.
     * @param resourceType the resource type to test
     * @return true if the type is contained; else false
     */
    public boolean contains(ResourceType resourceType) {
        return this.resourceType.equals(resourceType);
    }

    /**
     * Convert a list of stocks to its String representation.
     * @param stocks the list of stocks to convert
     * @return the converted String
     */
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
