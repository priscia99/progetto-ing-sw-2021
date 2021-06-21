package it.polimi.ingsw.server.model.singleplayer.action_token;

import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.singleplayer.SinglePlayerGame;

import java.util.ArrayList;

public abstract class ActionToken {

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

    public abstract void applyOn(SinglePlayerGame toApplyOn);
}
