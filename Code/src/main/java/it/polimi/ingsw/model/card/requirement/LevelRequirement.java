package it.polimi.ingsw.model.card.requirement;

import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.game.Player;

public class LevelRequirement extends Requirement{

    private final Color color;

    public LevelRequirement(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean isFulfilled(Player player) {
        return player.colorByLevel(2).contains(this.color);
    }

    @Override
    public String toString(){
        return "Requirement type: LEVEL 2 DEVELOPMENT CARD\n" +
                "\tRequired color: " + color.name() + "\n";
    }
}
