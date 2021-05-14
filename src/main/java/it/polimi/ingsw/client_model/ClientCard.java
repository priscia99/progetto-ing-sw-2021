package it.polimi.ingsw.client_model;

import it.polimi.ingsw.server_model.card.effect.Effect;
import it.polimi.ingsw.server_model.card.requirement.Requirement;

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