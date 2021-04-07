package it.polimi.ingsw.model.card.requirement;

import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.resource.ResourcePile;

import java.util.List;
import java.util.stream.Collectors;

public class ResourceRequirement extends Requirement{

    private final List<ResourcePile> resourcePiles;

    public ResourceRequirement(List<ResourcePile> resourcePiles) {
        this.resourcePiles = resourcePiles;
    }

    public List<ResourcePile> getResourcePiles() {
        return resourcePiles;
    }

    @Override
    public boolean isFulfilled(Player player) {
         return this.resourcePiles
                 .stream()
                 .allMatch(resourcePile ->
                         player.countByResource(resourcePile.getResourceType()) == resourcePile.getQuantity());
    }

    @Override
    public String toString(){
        StringBuilder buffer = new StringBuilder();
        buffer.append("Requirement type: RESOURCES\n");
        for(ResourcePile pile : resourcePiles){
            buffer.append("\tType: ").append(pile.getResourceType().name());
            buffer.append(" - Quantity: ").append(pile.getQuantity()).append("\n");
        }
        return buffer.toString();
    }
}
