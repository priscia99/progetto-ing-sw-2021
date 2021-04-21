package it.polimi.ingsw.client;

import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.effect.Effect;
import it.polimi.ingsw.model.card.requirement.Requirement;

public class ClientLeaderCard extends ClientCard implements Viewable {

    private final boolean active;

    public ClientLeaderCard(int id, Requirement requirement, Effect effect, boolean active) {
        super(id, requirement, effect);
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String getAssetLink() {
        // FIXME: complete me
        return null;
    }
}
