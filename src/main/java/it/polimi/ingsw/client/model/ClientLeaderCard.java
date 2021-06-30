package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.card.requirement.*;
import it.polimi.ingsw.server.model.card.effect.*;

import java.io.Serializable;

public class ClientLeaderCard extends ClientCard implements ClientAsset, Serializable {

    private boolean active;

    /**
     * Initialize a client leader card by passing all parameters from the server model
     * @param leaderCard leader card
     */
    public ClientLeaderCard(LeaderCard leaderCard) {
        super(leaderCard.getId(), leaderCard.getRequirement(), leaderCard.getEffect(), leaderCard.getVictoryPoints());
        this.active = leaderCard.isActive();
    }

    /**
     * Initialize a client leader card by passing all parameters
     * @param id leader card id
     * @param requirement leader card requirement
     * @param effect leader card effect
     * @param victoryPoints victory points provided by the leader card when activated
     * @param active card activation status
     */
    public ClientLeaderCard(String id, Requirement requirement, Effect effect, int victoryPoints, boolean active) {
        super(id, requirement, effect, victoryPoints);
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    @Override
    public String getAssetLink() {
        return this.getId().substring(1, this.getId().length());
    }
}
