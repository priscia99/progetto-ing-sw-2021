package it.polimi.ingsw.server.model.card.requirement;

import it.polimi.ingsw.server.model.card.color.ColorPile;
import it.polimi.ingsw.server.model.game.Player;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Extension of Requirement to specify the requirement of specific card color.
 */
public class ColorRequirement extends Requirement implements Serializable {

    private static final long serialVersionUID = 19L;
    private ArrayList<ColorPile> colorPiles;
    private static final String COLOR_REQUIREMENT_FORMAT = "[%s]";

    /**
     * Create a ColorRequirement object with a given set of colors.
     * @param colorPiles the list of required colors and their quantities
     */
    public ColorRequirement(ArrayList<ColorPile> colorPiles) {
        this.colorPiles = colorPiles;
    }


    public ColorRequirement(){}
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
        String colorString = ColorPile.pilesToString(colorPiles);
        return String.format(COLOR_REQUIREMENT_FORMAT, colorString);
    }
}
