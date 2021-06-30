package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.card.requirement.*;
import it.polimi.ingsw.server.model.card.effect.*;

import java.io.Serializable;

public abstract class ClientCard implements Serializable {

    private static final long serialVersionUID = 2L;
    private final String id;
    private final Requirement requirement;
    private final Effect effect;
    private final int victoryPoints;

    /**
     * Initialized a generic client card by passing all parameters
     * @param id the card id
     * @param requirement the card requirement
     * @param effect the card effect
     * @param victoryPoints victory points given by the card
     */
    public ClientCard(String id, Requirement requirement, Effect effect, int victoryPoints) {
        this.id = id;
        this.requirement = requirement;
        this.effect = effect;
        this.victoryPoints = victoryPoints;
    }

    public String getId() {
        return id;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public Effect getEffect() {
        return effect;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}
