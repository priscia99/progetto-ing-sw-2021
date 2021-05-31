package it.polimi.ingsw.server.model.card.requirement;

import it.polimi.ingsw.client.view.representation.ColorRequirementRepresentation;
import it.polimi.ingsw.client.view.representation.Representation;
import it.polimi.ingsw.server.model.card.color.ColorPile;
import it.polimi.ingsw.server.model.game.Player;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Extension of Requirement to specify the requirement of specific card color.
 */
public class ColorRequirement extends Requirement implements Serializable {

    private static final long serialVersionUID = 1L;
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

    @Override
    public Representation toRepresentation() {
        return new ColorRequirementRepresentation("", colorPiles);
    }
}
