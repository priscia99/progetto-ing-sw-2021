package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.exceptions.EmptyDepotException;
import it.polimi.ingsw.model.resource.ConsumableResource;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.List;
import java.util.stream.Collectors;

public class Strongbox extends Storage{

    public Strongbox(List<ConsumableResource> consumableResources) {
        super(consumableResources);
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
    public void addResource(ResourceType resourceType) {
        consumableResources.add(new ConsumableResource(resourceType));
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
        return found;
    }
}
