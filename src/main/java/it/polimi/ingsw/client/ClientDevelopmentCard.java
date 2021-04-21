package it.polimi.ingsw.client;

import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.card.effect.Effect;
import it.polimi.ingsw.model.card.requirement.Requirement;

public class ClientDevelopmentCard extends ClientCard{

    private final Color color;

    public ClientDevelopmentCard(int id, Requirement requirement, Effect effect, Color color) {
        super(id, requirement, effect);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
