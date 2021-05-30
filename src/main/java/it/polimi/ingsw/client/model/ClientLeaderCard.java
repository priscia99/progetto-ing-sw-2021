package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.card.requirement.*;
import it.polimi.ingsw.server.model.card.effect.*;
public class ClientLeaderCard extends ClientCard implements ClientAsset {

    private final boolean active;

    public ClientLeaderCard(LeaderCard leaderCard) {
        super(leaderCard.getId(), leaderCard.getRequirement(), leaderCard.getEffect(), leaderCard.getVictoryPoints());
        this.active = leaderCard.isActive();
    }

    public ClientLeaderCard(String id, Requirement requirement, Effect effect, int victoryPoints, boolean active) {
        super(id, requirement, effect, victoryPoints);
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String getAssetLink() {
        // FIXME: remove me (new getAssetLink in Representation)
        return null;
    }
}
