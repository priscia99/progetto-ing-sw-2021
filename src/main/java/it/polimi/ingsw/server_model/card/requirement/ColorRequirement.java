package it.polimi.ingsw.server_model.card.requirement;

import it.polimi.ingsw.server_model.card.color.ColorPile;
import it.polimi.ingsw.server_model.game.Player;

import java.util.ArrayList;

/**
 * Extension of Requirement to specify the requirement of specific card color.
 */
public class ColorRequirement extends Requirement{

    private final ArrayList<ColorPile> colorPiles;

    /**
     * Create a ColorRequirement object with a given set of colors.
     * @param colorPiles the list of required colors and their quantities
     */
    public ColorRequirement(ArrayList<ColorPile> colorPiles) {
        this.colorPiles = colorPiles;
    }

    /**
     *
     * @return the required color piles.
     */
    public ArrayList<ColorPile> getColorPiles() {
        return colorPiles;
    }

    @Override
    public boolean isFulfilled(Player player) {
        return this.colorPiles
                .stream()
                .allMatch(colorPile -> player.countByColor(colorPile.getColor()) >= colorPile.getQuantity());
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
