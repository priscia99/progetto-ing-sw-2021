package it.polimi.ingsw.server.model.card.effect;

import it.polimi.ingsw.server.model.resource.ResourceDepot;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.io.Serializable;

/**
 * Extension of effect to specify resources depot.
 */
public class DepotEffect  implements Effect,  Serializable {

    private static final long serialVersionUID = 1L;
    private final ResourceDepot resourceDepot;
    private static final String DEPOT_EFFECT_FORMAT = "%s DEPOT | Current state [%s, %s]";
    private EffectType effectType;
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

    public EffectType getEffectType() {
        return effectType;
    }

    @Override
    public String toString(){
        String firstResource = resourceDepot.getQuantity() > 0 ? resourceDepot.getResourceType().toString() : "EMPTY";
        String secondResource = resourceDepot.getQuantity() > 1 ? resourceDepot.getResourceType().toString() : "EMPTY";

        return String.format(DEPOT_EFFECT_FORMAT, resourceDepot.getResourceType().toString(), firstResource, secondResource);
    }
}
