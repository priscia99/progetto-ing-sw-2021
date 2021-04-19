package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.exceptions.EmptyDepotException;
import it.polimi.ingsw.exceptions.FullDepotException;
import it.polimi.ingsw.exceptions.IllegalResourceException;
import it.polimi.ingsw.model.locally_copiable.LocallyCopyable;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Warehouse implements LocallyCopyable<Warehouse.WarehouseLocalCopy> {

    private final Depot[] depots;

    // inner class for local copy (simple copy for clients)
    public static class WarehouseLocalCopy {

        private final ArrayList<Depot.DepotLocalCopy> depots;

        public WarehouseLocalCopy(ArrayList<Depot.DepotLocalCopy> depots) {
            this.depots = depots;
        }

        public Depot.DepotLocalCopy getDepot(int index) {
            return depots.get(index);
        }

        public boolean contains(ResourceType resourceType) {
            for (Depot.DepotLocalCopy depot: depots) {
                if (depot.getConsumableResources()
                        .stream()
                        .anyMatch(consumableResource -> consumableResource.getResourceType().equals(resourceType))
                ) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public WarehouseLocalCopy getLocalCopy() {
        return new WarehouseLocalCopy(
                Arrays.stream(this.depots)
                        .map(Depot::getLocalCopy)
                        .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    public Warehouse() {
        this.depots = new Depot[3];
        this.depots[0] = new Depot(1);
        this.depots[1] = new Depot(2);
        this.depots[2] = new Depot(3);
    }

    /**
     * Swap depots position in Warehouse and assign them new capacity based on the new position.
     * @param index1 index of the first depot (new index for the second depot)
     * @param index2 index of the second depot (new index for the first depot)
     * @throws IllegalArgumentException if indexes are not in [0;2]
     */
    public void swapDepot(int index1, int index2) {
        if(index1 < 0 || index1 > 2) {
            throw new IllegalArgumentException("Indexes must be in [0;2]");
        }
        if(index2 < 0 || index2 > 2) {
            throw new IllegalArgumentException("Indexes must be in [0;2]");
        }

        Depot tempDepot = this.depots[index1];
        this.depots[index1] = this.depots[index2];
        this.depots[index2] = tempDepot;

        this.depots[index1].setCapacity(index1+1);
        this.depots[index2].setCapacity(index2+1);
    }

    public Depot[] getDepots() {
        return depots;
    }

    public boolean isFull() {
        for(Depot d : depots){
            if (!d.isFull()) return false;
        }
        return true;
    }

    public void addToDepot(int depotIndex, ResourceType resourceType) {
        try{
            depots[depotIndex].addResource(resourceType);
        }
        catch (IllegalResourceException e) {
            e.printStackTrace();
            // TODO: Manage code behaviour when player is trying to add a resource of a wrong type in a warehouse depot
        }
        catch (FullDepotException e) {
            e.printStackTrace();
            // TODO: Manage code behaviour when player is trying to add a resource in a full warehouse depot
        }
    }

    public void removeConsumedResources(){
        for(Depot toClean : depots) {
            toClean.removeConsumedResources();
        }
    }


    // TODO is it useful ???
    public void removeFromDepot(int depotIndex) {
        try{
            depots[depotIndex].removeResource();
        }
        catch (EmptyDepotException e) {
            e.printStackTrace();
            // TODO: Manage code bahviour when player is trying to remove a resource from an empty warehouse depot
        }
    }
}
