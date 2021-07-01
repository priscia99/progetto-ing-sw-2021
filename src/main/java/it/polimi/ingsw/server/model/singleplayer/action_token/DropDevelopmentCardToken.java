package it.polimi.ingsw.server.model.singleplayer.action_token;

import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.singleplayer.SinglePlayerGame;

import java.util.Arrays;
import java.util.Stack;

public class DropDevelopmentCardToken extends ActionToken{
    Color developmentCardColor;

    DropDevelopmentCardToken(Color toDrop){
        developmentCardColor = toDrop;
    }


    /**
     * Remove two development card with selected color from market
     * @param toApplyOn Game into which apply token effect
     */
    @Override
    public void applyOn(SinglePlayerGame toApplyOn) {
        toApplyOn.getCardMarket().removeByColor(developmentCardColor, 2);
    }
}
