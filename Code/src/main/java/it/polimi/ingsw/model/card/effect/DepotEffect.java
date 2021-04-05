package it.polimi.ingsw.model.card.effect;

import it.polimi.ingsw.model.player_board.storage.Depot;
import it.polimi.ingsw.model.resource.ResourceType;

public class DepotEffect extends Effect{

    private final ResourceType resourceType;
    private final Depot depot;

    public DepotEffect( ResourceType resourceType, Depot depot) {
        this.effectType = EffectType.DEPOT;
        this.resourceType = resourceType;
        this.depot = depot;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public Depot getDepot() {
        return depot;
    }

    @Override
    public EffectType getEffectType() {
        return super.getEffectType();
    }
}
