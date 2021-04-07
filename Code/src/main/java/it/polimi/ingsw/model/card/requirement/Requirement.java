package it.polimi.ingsw.model.card.requirement;

import it.polimi.ingsw.model.game.Player;

public abstract class Requirement {
    public abstract boolean isFulfilled(Player player);
}
