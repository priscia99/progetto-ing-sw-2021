package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.card.color.*;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.card.requirement.*;
import it.polimi.ingsw.server.model.card.effect.*;

public class ClientDevelopmentCard extends ClientCard implements ClientAsset {

    private final Color color;
    private final int level;

    public ClientDevelopmentCard(DevelopmentCard developmentCard) {
        super(developmentCard.getId(), developmentCard.getRequirement(), developmentCard.getEffect(), developmentCard.getVictoryPoints());
        this.color = developmentCard.getColor();
        this.level = developmentCard.getLevel();
    }

    public ClientDevelopmentCard(String id, Requirement requirement, Effect effect, Color color, int level, int victoryPoints) {
        super(id, requirement, effect, victoryPoints);
        this.color = color;
        this.level = level;
    }

    public Color getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String getAssetLink() {
        return this.getId().substring(1, this.getId().length());
    }
}
