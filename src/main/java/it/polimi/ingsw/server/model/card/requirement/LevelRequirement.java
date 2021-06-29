package it.polimi.ingsw.server.model.card.requirement;

import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.game.Player;

import java.io.Serializable;

/**
 * Extension of Requirement to specify the requirement of owning level 2 cards of specific colors.
 */
public class LevelRequirement extends Requirement implements Serializable {

    private static final long serialVersionUID = 20L;
    private Color color;
    private static final String LEVEL_REQUIREMENT_FORMAT = "%s Lv 2";

    /**
     * Create a LevelRequirement object with a given color.
     * @param color the color of the wanted level 2 card
     */
    public LevelRequirement(Color color) {
        this.color = color;
    }

    /**
     * Create a default LevelRequirement object.
     */
    public LevelRequirement(){}

    /**
     *
     * @return the wanted color.
     */
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isFulfilled(Player player) {
        return player.colorByLevel(2).contains(this.color);
    }

    @Override
    public String toString(){
        return String.format(LEVEL_REQUIREMENT_FORMAT, color.toString());
    }
}
