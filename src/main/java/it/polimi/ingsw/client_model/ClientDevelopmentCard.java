package it.polimi.ingsw.client_model;

import it.polimi.ingsw.server_model.card.DevelopmentCard;
import it.polimi.ingsw.server_model.card.color.Color;
import it.polimi.ingsw.server_model.card.effect.Effect;
import it.polimi.ingsw.server_model.card.requirement.Requirement;

public class ClientDevelopmentCard extends ClientCard implements ClientAsset{

    private final Color color;

    public ClientDevelopmentCard(DevelopmentCard developmentCard) {
        super(developmentCard.getId(), developmentCard.getRequirement(), developmentCard.getEffect());
        this.color = developmentCard.getColor();
    }

    public ClientDevelopmentCard(String id, Requirement requirement, Effect effect, Color color) {
        super(id, requirement, effect);
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
