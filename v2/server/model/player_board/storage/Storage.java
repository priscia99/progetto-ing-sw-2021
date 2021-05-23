package v2.server.model.player_board.storage;

import it.polimi.ingsw.network.update.Update;
import it.polimi.ingsw.observer.Observable;
import v2.server.model.resource.ResourceStock;
import v2.server.model.resource.ResourceType;

import java.util.ArrayList;

public abstract class Storage extends Observable<Update> {

    protected ArrayList<ResourceStock> resourceStocks;

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
}
