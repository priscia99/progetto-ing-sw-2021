package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.effect.Effect;
import it.polimi.ingsw.model.card.requirement.Requirement;

public class ClientLeaderCard extends ClientCard implements Viewable {

    private final boolean active;

    public ClientLeaderCard(LeaderCard leaderCard) {
        super(leaderCard.getId(), leaderCard.getRequirement(), leaderCard.getEffect());
        this.active = leaderCard.isActive();
    }

    public ClientLeaderCard(String id, Requirement requirement, Effect effect, boolean active) {
        super(id, requirement, effect);
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String getAssetLink() {
        // FIXME: complete
        return null;
    }
}
