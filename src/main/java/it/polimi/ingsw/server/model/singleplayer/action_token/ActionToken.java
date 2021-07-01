package it.polimi.ingsw.server.model.singleplayer.action_token;

import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.singleplayer.SinglePlayerGame;

import java.util.ArrayList;

/**
 * Action token triggered at each end turn in single player game.
 */
public abstract class ActionToken {

    /**
     *
     * @return List of tokens accordingly to game rules
     */
    public static ArrayList<ActionToken> getStartingTokens(){
        ArrayList<ActionToken> tokens = new ArrayList<>();
        tokens.add(new AdvanceAndShuffleToken());
        tokens.add(new AdvanceBlackCrossToken());
        tokens.add(new DropDevelopmentCardToken(Color.BLUE));
        tokens.add(new DropDevelopmentCardToken(Color.GREEN));
        tokens.add(new DropDevelopmentCardToken(Color.YELLOW));
        tokens.add(new DropDevelopmentCardToken(Color.PURPLE));
        return tokens;
    }

    /**
     * Apply token effect to a game
     * @param toApplyOn Game into which apply token effect
     */
    public abstract void applyOn(SinglePlayerGame toApplyOn);
}
