package it.polimi.ingsw.server.model.singleplayer.action_token;

import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.singleplayer.SinglePlayerGame;

public class AdvanceAndShuffleToken extends ActionToken{

    @Override
    public void applyOn(SinglePlayerGame toApplyOn) {
        toApplyOn.advanceBlackCross(1);
        toApplyOn.shuffleTokens();
    }
}
