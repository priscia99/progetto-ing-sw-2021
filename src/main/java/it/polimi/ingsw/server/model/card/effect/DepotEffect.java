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
