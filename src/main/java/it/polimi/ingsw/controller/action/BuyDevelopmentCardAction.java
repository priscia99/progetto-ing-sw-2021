package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.server_model.card.DevelopmentCard;
import it.polimi.ingsw.server_model.game.Game;

public class BuyDevelopmentCardAction extends Action {
    private final int positionX;
    private final int positionY;
    private final int deckIndex;

    public BuyDevelopmentCardAction(int posX, int posY, int index) {
        this.positionX = posX;
        this.positionY = posY;
        this.deckIndex = index;
    }

    public void execute(Game currentGame){
        DevelopmentCard cardBought = currentGame.getCardMarket().sell(positionX,positionY, currentGame.getCurrentPlayer());
        currentGame.getCurrentPlayer().getPlayerBoard().addDevelopmentCard(cardBought, deckIndex);
    }
}
