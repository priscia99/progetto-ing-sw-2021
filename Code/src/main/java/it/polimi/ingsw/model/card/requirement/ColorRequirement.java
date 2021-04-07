package it.polimi.ingsw.model.card.requirement;

import it.polimi.ingsw.model.card.color.ColorPile;
import it.polimi.ingsw.model.game.Player;

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
    public boolean isFulfilled(Player player) {
        return this.colorPiles
                .stream()
                .allMatch(colorPile -> player.countByColor(colorPile.getColor()) == colorPile.getQuantity());
    }

    @Override
    public String toString(){
        StringBuilder buffer = new StringBuilder();
        buffer.append("Requirement type: COLOR\n");
        for(ColorPile pile : colorPiles){
            buffer.append("\tColor: ").append(pile.getColor().name());
            buffer.append(" - Quantity: ").append(pile.getQuantity()).append("\n");
        }
        return buffer.toString();
    }
}
