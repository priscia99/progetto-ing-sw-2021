package it.polimi.ingsw.model.card.requirement;

import it.polimi.ingsw.model.card.color.Color;

public class LevelRequirement extends Requirement{

    private final Color color;

    public LevelRequirement(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean isFulfilled() {
        return false;
    }
}
