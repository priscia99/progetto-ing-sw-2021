package it.polimi.ingsw.model.card.effect;

import it.polimi.ingsw.model.player_board.storage.Depot;
import it.polimi.ingsw.model.resource.ResourceType;

/**
 * Extension of effect to specify resources depot.
 */
public class DepotEffect extends Effect{

    private final ResourceType resourceType;
    private final Depot depot;

    /**
     * Create a DepotEffect object.
     * @param resourceType the specific type of resource accepted
     * @param depot the depot to store the resources in
     */
    public DepotEffect( ResourceType resourceType, Depot depot) {
        this.effectType = EffectType.DEPOT;
        this.resourceType = resourceType;
        this.depot = depot;
    }

    /**
     *
     * @return the type of accepted resources
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     *
     * @return the depot in which resources are stored
     */
    public Depot getDepot() {
        return depot;
    }

    @Override
    public EffectType getEffectType() {
        return super.getEffectType();
    }

    public String toString(){
        return "Effect type: ADDITIONARY DEPOT \n" +
                "\tCreate a 2-resources capacity depot containing " + resourceType.name() + "S";
    }
}
