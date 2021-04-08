package it.polimi.ingsw.model.card.requirement;

import it.polimi.ingsw.model.card.color.ColorPile;
import it.polimi.ingsw.model.game.Player;

import java.util.ArrayList;

public class ColorRequirement extends Requirement{

    private final ArrayList<ColorPile> colorPiles;

    public ColorRequirement(ArrayList<ColorPile> colorPiles) {
        this.colorPiles = colorPiles;
    }

    public ArrayList<ColorPile> getColorPiles() {
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
