package v2.client.view.client_model;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.card.effect.Effect;
import it.polimi.ingsw.model.card.requirement.Requirement;
import it.polimi.ingsw.view.client_model.ClientAsset;
import it.polimi.ingsw.view.client_model.ClientCard;

public class ClientDevelopmentCard extends ClientCard implements ClientAsset {

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
