package it.polimi.ingsw.model.card.requirement;

import it.polimi.ingsw.model.game.Player;

/**
 * Abstract definition of requirements as objects that share the possibility of being fulfilled or not.
 */
public abstract class Requirement {

    /**
     * Test if the requirement is fulfilled by a player.
     * @param player the player to check
     * @return true if the requirement is fulfilled, false if not.
     */
    public abstract boolean isFulfilled(Player player);
}
