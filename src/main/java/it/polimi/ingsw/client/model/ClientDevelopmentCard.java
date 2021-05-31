package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.card.color.*;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.card.requirement.*;
import it.polimi.ingsw.server.model.card.effect.*;

public class ClientDevelopmentCard extends ClientCard implements ClientAsset {

    private final Color color;

    public ClientDevelopmentCard(DevelopmentCard developmentCard) {
        super(developmentCard.getId(), developmentCard.getRequirement(), developmentCard.getEffect(), developmentCard.getVictoryPoints());
        this.color = developmentCard.getColor();
    }

    public ClientDevelopmentCard(String id, Requirement requirement, Effect effect, Color color, int victoryPoints) {
        super(id, requirement, effect, victoryPoints);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String getAssetLink() {
        // TODO: complete me
        return null;
    }
}
