package it.polimi.ingsw_old.view.client_model;

import it.polimi.ingsw_old.model.card.effect.Effect;
import it.polimi.ingsw_old.model.card.requirement.Requirement;

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
