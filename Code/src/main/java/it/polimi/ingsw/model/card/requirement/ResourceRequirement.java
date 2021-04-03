package it.polimi.ingsw.model.card.requirement;

import it.polimi.ingsw.model.resource.ResourcePile;

import java.util.List;

public class ResourceRequirement extends Requirement{

    private final List<ResourcePile> resourcePiles;

    public ResourceRequirement(List<ResourcePile> resourcePiles) {
        this.resourcePiles = resourcePiles;
    }

    public List<ResourcePile> getResourcePiles() {
        return resourcePiles;
    }

    @Override
    public boolean isFulfilled() {
        return false;
    }
}
