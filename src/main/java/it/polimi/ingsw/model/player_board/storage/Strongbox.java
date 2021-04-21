package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.model.locally_copiable.LocallyCopyable;
import it.polimi.ingsw.model.resource.ConsumableResource;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;

public class Strongbox extends Storage {

    public Strongbox() {
        super();
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
