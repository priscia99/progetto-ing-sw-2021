package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

import java.util.Map;

public class BuyDevelopmentCardAction extends TurnAction{

    public BuyDevelopmentCardAction() {
        this.turnActionType = TurnActionType.BUY_DEVELOPMENT_CARD;
    }

    public void execute(Game currentGame, Map<String, Object> params) {
        //TODO: move params parsing in controller
        int xPosition = (int) params.get("xPosition");
        int yPosition = (int) params.get("yPosition");
        int deckIndex = (int) params.get("deckIndex");
        DevelopmentCard cardBought = currentGame.getCardMarket().sell(xPosition, yPosition, currentGame.getCurrentPlayer());
        currentGame.getCurrentPlayer().getPlayerBoard().addDevelopmentCard(cardBought, deckIndex);
    }
}
