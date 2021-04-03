package it.polimi.ingsw.model.card.requirement;

import it.polimi.ingsw.model.card.color.ColorPile;

import java.util.List;

public class ColorRequirement extends Requirement{

    private final List<ColorPile> colorPiles;

    public ColorRequirement(List<ColorPile> colorPiles) {
        this.colorPiles = colorPiles;
    }

    public List<ColorPile> getColorPiles() {
        return colorPiles;
    }

    @Override
    public boolean isFulfilled() {
        return false;
    }
}
