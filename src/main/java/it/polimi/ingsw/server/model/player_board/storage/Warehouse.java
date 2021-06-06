package it.polimi.ingsw.server.model.player_board.storage;

import it.polimi.ingsw.network.message.from_server.FaithPathMessage;
import it.polimi.ingsw.network.message.from_server.WarehouseMessage;
import it.polimi.ingsw.server.model.resource.ResourceDepot;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Warehouse extends Storage {

    public Warehouse() {
        super();
        for (int i = 0; i < 3; i++) {
            this.resourceStocks.add(new ResourceDepot(i+1));
        }
    }

    /**
     * Swap depots position in Warehouse and assign them new capacity based on the new position.
     * @param index1 index of the first depot (new index for the second depot)
     * @param index2 index of the second depot (new index for the first depot)
     * @throws IllegalArgumentException if indexes are not in [0;2]
     */
    public void swap(int index1, int index2) {
        this.checkValidIndex(index1);
        this.checkValidIndex(index2);

        Collections.swap(this.resourceStocks, index1, index2);
        ((ResourceDepot) this.getResourceStock(index2)).setCapacity(index2+1);
        ((ResourceDepot) this.getResourceStock(index1)).setCapacity(index1+1);
    }

    /**
     * Returns the depot that matches with the address given as input
     * @param index index of the depot to return
     * @return the depot that matches with the address given as input
     */
    public ResourceDepot getDepot(int index) {
        this.checkValidIndex(index);
        return (ResourceDepot) this.getResourceStock(index);
    }

    /**
     * Check if the entire warehouse is full
     * @return true if the warehouse is full
     * @return false otherwise
     */
    public boolean isFull() {
        return this.resourceStocks.stream()
                .map(resourceStock -> (ResourceDepot) resourceStock)
                .allMatch(ResourceDepot::isFull);
    }

    /**
     * Check if the entire warehouse is empty
     * @return true if the warehouse is empty
     * @return false otherwise
     */
    public boolean isEmpty() {
        return this.resourceStocks.stream()
                .map(resourceStock -> (ResourceDepot) resourceStock)
                .allMatch(ResourceStock::isEmpty);
    }

    /**
     * Add a new resource into the specified depot
     * @param index index of the depot
     * @param resourceType type of the resource to insert
     */
    public void addToDepot(int index, ResourceType resourceType){
        this.checkValidIndex(index);
        if (this.getResourceStock(index).isEmpty()) {
            if (this.contains(resourceType)) {
                throw new IllegalArgumentException("An other depot in the warehouse already contains this resource type");
            } else {
                ((ResourceDepot) this.getResourceStock(index)).setResourceType(resourceType);
            }
        }
        this.getResourceStock(index).incrementResource(resourceType);

    }

    /**
     * Remove a resource given its type and the index from which it needs to be removed
     * @param index index of the depot
     * @param resourceType type of the resource to delete
     */
    public void removeFromDepot(int index, ResourceType resourceType) {
        this.checkValidIndex(index);
        this.getResourceStock(index).decrementResource(resourceType);
        if (this.getResourceStock(index).isEmpty()) {
            ((ResourceDepot) this.getResourceStock(index)).setResourceType(ResourceType.BLANK);
        }

    }

    /**
     * Counts and returns the number of resources of the requested type
     * @param resourceType the type of the resource to be counted
     * @return the number of resources of that type
     */
    @Override
    public int countByResourceType(ResourceType resourceType) {
        return this.resourceStocks
                .stream()
                .filter(resourceStock -> resourceStock.contains(resourceType))
                .map(ResourceStock::getQuantity)
                .reduce(0, Integer::sum);
    }

    public boolean contains(ResourceType resourceType) {
        return this.resourceStocks
                .stream()
                .anyMatch(resourceStock -> resourceStock.contains(resourceType));
    }

    /**
     * Check if the given index is valid
     * @param index index to check
     */
    private void checkValidIndex(int index) {
        if(index < 0 || index > 2) {
            throw new IllegalArgumentException("Indexes must be in [0;2]");
        }
    }

}
