package it.polimi.ingsw_old.view.client_model;

import it.polimi.ingsw_old.model.card.LeaderCard;
import it.polimi.ingsw_old.model.card.effect.Effect;
import it.polimi.ingsw_old.model.card.requirement.Requirement;

public class ClientLeaderCard extends ClientCard implements ClientAsset {

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
