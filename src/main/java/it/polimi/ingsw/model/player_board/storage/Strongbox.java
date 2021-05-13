package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.model.resource.ResourceStock;
import it.polimi.ingsw.model.resource.ResourceType;

public class Strongbox extends Storage {

    public Strongbox() {
        super();
        this.resourceStocks.add(new ResourceStock(ResourceType.COIN));
        this.resourceStocks.add(new ResourceStock(ResourceType.STONE));
        this.resourceStocks.add(new ResourceStock(ResourceType.SHIELD));
        this.resourceStocks.add(new ResourceStock(ResourceType.SERVANT));
    }

    public void addResource(ResourceType resourceType) {
        this.resourceStocks.stream()
                .filter(resourceStock -> resourceStock.getResourceType().equals(resourceType))
                .forEach(resourceStock -> resourceStock.incrementResource(resourceType));
        notify(this);
    }

    public void removeResource(ResourceType resourceType) {
        this.resourceStocks.stream()
                .filter(resourceStock -> resourceStock.getResourceType().equals(resourceType))
                .forEach(resourceStock -> resourceStock.decrementResource(resourceType));
        notify(this);
    }

    @Override
    public int countByResourceType(ResourceType resourceType) {
        return this.resourceStocks.stream()
                .filter(resourceStock -> resourceStock.getResourceType().equals(resourceType))
                .map(ResourceStock::getQuantity)
                .reduce(0, Integer::sum);
    }

    public boolean contains(ResourceType resourceType) {
        return this.resourceStocks.stream()
                .anyMatch(resourceStock -> resourceStock.getResourceType().equals(resourceType) && resourceStock.getQuantity() > 0);
    }
}
