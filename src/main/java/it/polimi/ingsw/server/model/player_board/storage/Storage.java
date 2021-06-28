package it.polimi.ingsw.server.model.player_board.storage;

import it.polimi.ingsw.client.controller.ClientController;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.util.ArrayList;

/**
 * Abstract class that models a generic storage of resources. Observable by a controller to monitor changes.
 */
public abstract class Storage extends Observable<Message<ClientController>> {

    protected ArrayList<ResourceStock> resourceStocks;

    /**
     * Create a new empty Storage object.
     */
    public Storage() {
        this.resourceStocks = new ArrayList<>();
    }

    /**
     * Return the resource stock that matches with the given index
     * @param index the index of the resource stock
     * @return the resource stock with that index
     */
    public ResourceStock getResourceStock(int index) {
        return resourceStocks.get(index);
    }

    /**
     * Get the resource stocks list
     * @return the resource stocks list
     */
    public ArrayList<ResourceStock> getResourceStocks() {
        return resourceStocks;
    }

    /**
     * Check if the storage is empty
     * @return true if the storage is empty
     * @return false otherwise
     */
    public boolean isEmpty() {
        return this.resourceStocks.stream().allMatch(ResourceStock::isEmpty);
    }

    /**
     * Counts and returns the number of resources of the requested type
     * @param resourceType the type of the resource to be counted
     * @return the number of resources of that type
     */
    public abstract int countByResourceType(ResourceType resourceType);

    /**
     * @return the quantity of resources contained into the storage
     */
    public int getResourceCount(){
        return this.resourceStocks.stream().mapToInt(ResourceStock::getQuantity).sum();
    }

    /**
     * Consume a resource stock of the storage.
     * @param toConsume the stock to consume
     * @throws Exception
     */
    public void consume(ResourceStock toConsume) throws Exception {
        ResourceStock consumed = this.resourceStocks.stream()
                .filter(resourceStock -> resourceStock.getResourceType().equals(toConsume.getResourceType()))
                .findFirst().get();
        for(int i = 0; i<toConsume.getQuantity(); i++) consumed.decrementResource(toConsume.getResourceType());
    }
}
