package it.polimi.ingsw.server.model.card.requirement;

import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.resource.ResourceStock;

import java.io.Serializable;
import java.util.List;

/**
 * Extension of Requirement to specify the requirement of specific quantities of determined resources.
 */
public class ResourceRequirement extends Requirement implements Serializable {

    private static final long serialVersionUID = 1L;
    private final List<ResourceStock> resourceStocks;

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

    @Override
    public String toString(){
        StringBuilder buffer = new StringBuilder();
        buffer.append("Requirement type: RESOURCES\n");
        for(ResourceStock pile : resourceStocks){
            buffer.append("\tType: ").append(pile.getResourceType().name());
            buffer.append(" - Quantity: ").append(pile.getQuantity()).append("\n");
        }
        return buffer.toString();
    }
}
