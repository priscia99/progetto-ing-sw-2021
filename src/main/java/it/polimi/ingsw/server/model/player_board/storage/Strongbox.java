package it.polimi.ingsw.server.model.player_board.storage;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.network.message.from_server.StrongboxMessage;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.util.stream.Collectors;

public class Strongbox extends Storage {

    public Strongbox() {
        super();
        this.resourceStocks.add(new ResourceStock(ResourceType.COIN));
        this.resourceStocks.add(new ResourceStock(ResourceType.STONE));
        this.resourceStocks.add(new ResourceStock(ResourceType.SHIELD));
        this.resourceStocks.add(new ResourceStock(ResourceType.SERVANT));
    }

    /**
     * Adds a single resource unit of the given type to the strongbox.
     * @param resourceType the type of the resource to be added
     */
    // FIXME implement resource quantity increment
    public void addResource(ResourceType resourceType) throws GameException {
        for(ResourceStock resourceStock : this.resourceStocks.stream()
                .filter(resourceStock -> resourceStock.getResourceType()
                        .equals(resourceType)).collect(Collectors.toList())){
            resourceStock.incrementResource(resourceType);
        }
    }

    /**
     * It removes from the strongbox a single unit of resource of the type given in input
     * @param resourceType the type of the resource to be removed
     */
    public void removeResource(ResourceType resourceType) throws GameException {
        for(ResourceStock resourceStock : this.resourceStocks.stream()
                .filter(resourceStock -> resourceStock.getResourceType()
                        .equals(resourceType)).collect(Collectors.toList())){
            resourceStock.decrementResource(resourceType);
        }
    }

    /**
     * Counts and returns the number of resources of the requested type
     * @param resourceType the type of the resource to be counted
     * @return the number of resources of that type
     */
    @Override
    public int countByResourceType(ResourceType resourceType) {
        return this.resourceStocks.stream()
                .filter(resourceStock -> resourceStock.getResourceType().equals(resourceType))
                .map(ResourceStock::getQuantity)
                .reduce(0, Integer::sum);
    }

    /**
     * It checks if a resource of the type given in input is present in the strongbox
     * @param resourceType Type of resource to be checked
     * @return true if a resource of the type given as input is present
     * @return false otherwise
     */
    public boolean contains(ResourceType resourceType) {
        return this.resourceStocks.stream()
                .anyMatch(resourceStock -> resourceStock.getResourceType().equals(resourceType) && resourceStock.getQuantity() > 0);
    }

    public boolean canConsume(ResourceStock toConsume){
        return this.resourceStocks.stream()
                .filter(resourceStock -> resourceStock.getResourceType().equals(toConsume.getResourceType()))
                .findFirst().get().getQuantity() >= toConsume.getQuantity();
    }


}
