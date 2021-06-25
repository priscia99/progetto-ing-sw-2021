package it.polimi.ingsw.server.model.card.requirement;

import it.polimi.ingsw.server.model.game.Player;

import java.io.Serializable;

/**
 * Abstract definition of requirements as objects that share the possibility of being fulfilled or not.
 */
public abstract class Requirement implements Serializable {

    /**
     * Test if the requirement is fulfilled by a player.
     * @param player the player to check
     * @return true if the requirement is fulfilled, false if not.
     */
    public abstract boolean isFulfilled(Player player);
}
