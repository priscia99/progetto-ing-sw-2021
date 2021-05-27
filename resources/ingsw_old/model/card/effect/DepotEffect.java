package it.polimi.ingsw_old.model.card.effect;

import it.polimi.ingsw_old.model.resource.ResourceDepot;
import it.polimi.ingsw_old.model.resource.ResourceType;

/**
 * Extension of effect to specify resources depot.
 */
public class DepotEffect extends Effect{

    private final ResourceDepot resourceDepot;

    /**
     * Create a DepotEffect object.
     * @param resourceType the specific type of resource accepted
     */
    public DepotEffect( ResourceType resourceType) {
        this.effectType = EffectType.DEPOT;
        this.resourceDepot = new ResourceDepot(resourceType, 2);
    }

    /**
     *
     * @return the type of accepted resources
     */
    public ResourceType getResourceType() {
        return this.resourceDepot.getResourceType();
    }

    /**
     *
     * @return the depot in which resources are stored
     */
    public ResourceDepot getDepot() {
        return this.resourceDepot;
    }

    @Override
    public EffectType getEffectType() {
        return super.getEffectType();
    }

    public String toString(){
        return "Effect type: ADDITIONARY DEPOT \n" +
                "\tCreate a 2-resources capacity depot containing " + this.getResourceType().name() + "S";
    }
}
