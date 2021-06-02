package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.card.requirement.*;
import it.polimi.ingsw.server.model.card.effect.*;

import java.io.Serializable;

public abstract class ClientCard implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String id;
    private final Requirement requirement;
    private final Effect effect;
    private final int victoryPoints;

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