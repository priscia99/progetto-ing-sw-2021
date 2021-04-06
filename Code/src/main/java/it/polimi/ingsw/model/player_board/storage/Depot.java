package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.exceptions.EmptyDepotException;
import it.polimi.ingsw.exceptions.FullDepotException;
import it.polimi.ingsw.exceptions.IllegalResourceException;
import it.polimi.ingsw.model.resource.ConsumableResource;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.List;

public class Depot extends Storage{

    private int capacity;

    public Depot(int capacity) {
        super();
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public Depot() {
        super();
    }

    @Override
    public List<ConsumableResource> getConsumableResources() {
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
    }

    @Override
    public void removeConsumedResources(){ super.removeConsumedResources(); }

    public void removeResource() throws EmptyDepotException{
        if(isEmpty())
            throw new EmptyDepotException("Empty depot.");
        else
            consumableResources.remove(consumableResources.size()-1);

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
