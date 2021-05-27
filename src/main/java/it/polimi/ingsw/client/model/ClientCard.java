package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.card.requirement.*;
import it.polimi.ingsw.server.model.card.effect.*;

public abstract class ClientCard {

    private final String id;
    private final Requirement requirement;
    private final Effect effect;

    public ClientCard(String id, Requirement requirement, Effect effect) {
        this.id = id;
        this.requirement = requirement;
        this.effect = effect;
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
}
