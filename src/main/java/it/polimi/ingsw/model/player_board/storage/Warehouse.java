package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.model.resource.ResourceDepot;
import it.polimi.ingsw.model.resource.ResourceStock;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.Collections;

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
    public void swapDepots(int index1, int index2) {
        this.checkValidIndex(index1);
        this.checkValidIndex(index2);

        Collections.swap(this.resourceStocks, index1, index2);
        ((ResourceDepot) this.getResourceStock(index2)).setCapacity(index2+1);
        ((ResourceDepot) this.getResourceStock(index1)).setCapacity(index1+1);

        // TODO add messageType
        notify(this, null);
    }

    public ResourceDepot getDepot(int index) {
        this.checkValidIndex(index);
        return (ResourceDepot) this.getResourceStock(index);
    }

    public boolean isFull() {
        return this.resourceStocks.stream()
                .map(resourceStock -> (ResourceDepot) resourceStock)
                .allMatch(ResourceDepot::isFull);
    }

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
        // TODO: Check if a resource of the same type is in another depot
        this.checkValidIndex(index);
        if (this.getResourceStock(index).isEmpty()) {
            ((ResourceDepot) this.getResourceStock(index)).setResourceType(resourceType);
        }
        this.getResourceStock(index).incrementResource(resourceType);

        // TODO add messageType
        notify(this, null);
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

        // TODO add messageType
        notify(this, null);
    }

    @Override
    public int countByResourceType(ResourceType resourceType) {
        return this.resourceStocks.stream()
                .filter(resourceStock -> resourceStock.contains(resourceType))
                .map(ResourceStock::getQuantity)
                .reduce(0, Integer::sum);
    }

    private void checkValidIndex(int index) {
        if(index < 0 || index > 2) {
            throw new IllegalArgumentException("Indexes must be in [0;2]");
        }
    }
}
