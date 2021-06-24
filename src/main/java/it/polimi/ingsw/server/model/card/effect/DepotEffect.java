package it.polimi.ingsw.server.model.card.effect;

import it.polimi.ingsw.server.model.resource.ResourceDepot;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.io.Serializable;

/**
 * Extension of effect to specify resources depot.
 */
public class DepotEffect extends Effect implements Serializable {

    private static final long serialVersionUID = 1L;
    private final ResourceDepot resourceDepot;
    private static final String DEPOT_EFFECT_FORMAT = "%s DEPOT | Current state [%s, %s]";

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
        String firstResource = resourceDepot.getQuantity() > 0 ? resourceDepot.getResourceType().toString() : "EMPTY";
        String secondResource = resourceDepot.getQuantity() > 1 ? resourceDepot.getResourceType().toString() : "EMPTY";

        return String.format(DEPOT_EFFECT_FORMAT, resourceDepot.getResourceType().toString(), firstResource, secondResource);
    }
}
