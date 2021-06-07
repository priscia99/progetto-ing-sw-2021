package it.polimi.ingsw.server.model.card.requirement;

import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Extension of Requirement to specify the requirement of specific quantities of determined resources.
 */
public class ResourceRequirement extends Requirement implements Serializable {

    private static final long serialVersionUID = 1L;
    private final List<ResourceStock> resourceStocks;
    private static final String RESOURCE_REQUIREMENT_FORMAT = "[%s]";

    /**
     * Create a ResourceRequirement object with a given list of resource piles.
     * @param resourceStocks the list of wanted resource piles (type and quantities)
     */
    public ResourceRequirement(List<ResourceStock> resourceStocks) {
        this.resourceStocks = resourceStocks;
    }

    /**
     *
     * @return return the list of resource piles.
     */
    public List<ResourceStock> getResourceStocks() {
        return resourceStocks;
    }

    @Override
    public boolean isFulfilled(Player player) {
         return this.resourceStocks
                 .stream()
                 .allMatch(resourcePile ->
                         player.countByResource(resourcePile.getResourceType()) >= resourcePile.getQuantity());
    }

    public boolean isFulfilled(HashMap<ResourcePosition, ResourceStock> toConsume){
        return !this.resourceStocks.stream().map(
                stock-> {
                    Optional<ResourceStock> consumedType = toConsume.values().stream()
                            .filter( c -> c.getResourceType() == stock.getResourceType())
                            .findFirst();
                    int quantity = consumedType.map(ResourceStock::getQuantity).orElse(0);
                    return quantity == stock.getQuantity();
                }
        ).collect(Collectors.toList()).contains(false);
    }

    @Override
    public String toString(){
        String resourceString = ResourceStock.stocksToString(new ArrayList<>(resourceStocks));
        return String.format(RESOURCE_REQUIREMENT_FORMAT, resourceString);
    }
}
