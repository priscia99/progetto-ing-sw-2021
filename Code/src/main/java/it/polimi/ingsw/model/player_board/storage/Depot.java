package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.exceptions.EmptyDepotException;
import it.polimi.ingsw.exceptions.FullDepotException;
import it.polimi.ingsw.exceptions.IllegalResourceException;
import it.polimi.ingsw.model.locally_copiable.LocallyCopyable;
import it.polimi.ingsw.model.resource.ConsumableResource;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class Depot extends Storage implements LocallyCopyable<Depot.DepotLocalCopy> {

    private final int capacity;

    // inner class for local copy (simple copy for clients)
    public static class DepotLocalCopy {

        private final int capacity;
        private final ArrayList<ConsumableResource> consumableResources;

        public DepotLocalCopy(int capacity, ArrayList<ConsumableResource> consumableResources) {
            this.capacity = capacity;
            this.consumableResources = consumableResources;
        }

        public int getOccupiedSpace() {
            return consumableResources.size();
        }

        public int getCapacity() {
            return capacity;
        }

        public ArrayList<ConsumableResource> getConsumableResources() {
            return consumableResources;
        }

        public boolean contains(ResourceType resourceType) {
            return this.consumableResources
                    .stream()
                    .anyMatch(consumableResource -> consumableResource.getResourceType().equals(resourceType));
        }
    }

    @Override
    public DepotLocalCopy getLocalCopy() {
        return new DepotLocalCopy(this.capacity, this.consumableResources);
    }

    public Depot(int capacity) {
        super();
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public ArrayList<ConsumableResource> getConsumableResources() {
        return super.getConsumableResources();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public void addResource(ResourceType resourceType) throws IllegalResourceException, FullDepotException{
        try{
            if(getResourceType() != resourceType){
                throw new IllegalResourceException("Requested type is " + resourceType.name() + ", but this depot is " + getResourceType().name() + " only.");
            }
        }catch (EmptyDepotException e){
            e.printStackTrace();
        }
        if(isFull())
            throw new FullDepotException("Full depot");
        else
            consumableResources.add(new ConsumableResource(resourceType));

            notify(this.consumableResources);
    }

    @Override
    public void removeConsumedResources(){
        super.removeConsumedResources();

        notify(this.consumableResources);
    }

    public void removeResource() throws EmptyDepotException{
        if(isEmpty())
            throw new EmptyDepotException("Empty depot.");
        else
            consumableResources.remove(consumableResources.size()-1);
            notify(this.consumableResources);

    }

    public ResourceType getResourceType() throws EmptyDepotException{
        // Returns the resource type of the first element of resources list, if present.
        if(!isEmpty())
            return consumableResources.get(0).getResourceType();
        else throw new EmptyDepotException("Empty depot.");
    }

    public boolean isFull(){
        if(isEmpty())
            return false;
        return capacity == consumableResources.size();
    }
}
