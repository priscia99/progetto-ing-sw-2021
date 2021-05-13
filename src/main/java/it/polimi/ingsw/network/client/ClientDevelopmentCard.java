package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.card.effect.Effect;
import it.polimi.ingsw.model.card.requirement.Requirement;

public class ClientDevelopmentCard extends ClientCard implements Viewable{

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
