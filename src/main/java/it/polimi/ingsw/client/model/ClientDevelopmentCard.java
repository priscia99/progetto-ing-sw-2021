package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.card.color.*;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.card.requirement.*;
import it.polimi.ingsw.server.model.card.effect.*;

import java.io.Serializable;

public class ClientDevelopmentCard extends ClientCard implements ClientAsset, Serializable {

    private static final long serialVersionUID = 1509L;
    private final Color color;
    private final int level;

    /**
     * Initialize a client development card by passing a card from the server model
     * @param developmentCard development card
     */
    public ClientDevelopmentCard(DevelopmentCard developmentCard) {
        super(developmentCard.getId(), developmentCard.getRequirement(), developmentCard.getEffect(), developmentCard.getVictoryPoints());
        this.color = developmentCard.getColor();
        this.level = developmentCard.getLevel();
    }

    /**
     * Initialize a client development card by passing all parameters
     * @param id development card id
     * @param requirement the requirement of the development card
     * @param effect the effetct of the development card
     * @param color color of the development card
     * @param level level of the development card
     * @param victoryPoints number of victory points givent by the development card
     */
    public ClientDevelopmentCard(String id, Requirement requirement, Effect effect, Color color, int level, int victoryPoints) {
        super(id, requirement, effect, victoryPoints);
        this.color = color;
        this.level = level;
    }

    /**
     *
     * @return the color of the development card
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @return the level of the development card
     */
    public int getLevel() {
        return level;
    }

    @Override
    public String getAssetLink() {
        return this.getId().substring(1, this.getId().length());
    }
}
