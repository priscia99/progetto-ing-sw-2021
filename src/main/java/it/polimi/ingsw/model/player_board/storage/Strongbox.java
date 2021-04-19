package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.model.locally_copiable.LocallyCopyable;
import it.polimi.ingsw.model.resource.ConsumableResource;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;

public class Strongbox extends Storage implements LocallyCopyable<Strongbox.StrongboxLocalCopy> {

    public Strongbox() {
        super();
    }

    public static class StrongboxLocalCopy {

        private final ArrayList<ConsumableResource> consumableResources;

        public StrongboxLocalCopy(ArrayList<ConsumableResource> consumableResources) {
            this.consumableResources = consumableResources;
        }

        public ArrayList<ConsumableResource> getConsumableResources() {
            return consumableResources;
        }

        public int getQuantityByType(ResourceType resourceType) {
            return (int) this.consumableResources
                    .stream()
                    .filter(consumableResource -> consumableResource.getResourceType().equals(resourceType))
                    .count();
        }

        public boolean contains(ResourceType resourceType) {
            return this.consumableResources
                    .stream()
                    .anyMatch(consumableResource -> consumableResource.getResourceType().equals(resourceType));
        }
    }

    @Override
    public StrongboxLocalCopy getLocalCopy() {
        return new StrongboxLocalCopy(this.consumableResources);
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
    public void addResource(ResourceType resourceType) {
        consumableResources.add(new ConsumableResource(resourceType));

        notify(this.consumableResources);
    }

    @Override
    public void removeConsumedResources(){ super.removeConsumedResources(); }

    public boolean removeResourceType(ResourceType resourceType){
        boolean found = false;
        if(isEmpty()){
            return false;
        }
        else{
            for(int pos = 0; !found && pos<consumableResources.size(); pos++){
                if(consumableResources.get(pos).getResourceType() == resourceType){
                    found = true;
                    consumableResources.remove(pos);
                }
            }
        }

        notify(this.consumableResources);

        return found;
    }
}
