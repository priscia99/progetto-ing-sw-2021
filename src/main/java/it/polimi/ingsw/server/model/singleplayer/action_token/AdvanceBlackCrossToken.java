package it.polimi.ingsw.server.model.singleplayer.action_token;

import it.polimi.ingsw.server.model.singleplayer.SinglePlayerGame;

public class AdvanceBlackCrossToken extends ActionToken {

    /**
     * Increase black cross position by two
     * @param toApplyOn Game into which apply token effect
     */
    @Override
    public void applyOn(SinglePlayerGame toApplyOn) {
        toApplyOn.advanceBlackCross(2);
    }
}
