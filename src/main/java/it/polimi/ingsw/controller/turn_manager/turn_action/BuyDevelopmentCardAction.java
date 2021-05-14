package it.polimi.ingsw.controller.turn_manager.turn_action;

import it.polimi.ingsw.server_model.card.DevelopmentCard;
import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.controller.turn_manager.action_params.BuyDevelopmentCardParams;

public class BuyDevelopmentCardAction extends TurnAction{

    public BuyDevelopmentCardAction() {
        this.turnActionType = TurnActionType.BUY_DEVELOPMENT_CARD;
    }

    public void execute(Game currentGame, BuyDevelopmentCardParams params){
        DevelopmentCard cardBought = currentGame.getCardMarket().sell(params.getXPosition(), params.getYPosition(), currentGame.getCurrentPlayer());
        currentGame.getCurrentPlayer().getPlayerBoard().addDevelopmentCard(cardBought, params.getDeckIndex());
    }
}
