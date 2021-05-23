package v2.server.controller.action;

import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.game.Game;

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
        if(currentGame.getCurrentPlayer().hasDoneMainAction()) throw new InvalidActionException();
        DevelopmentCard cardBought = currentGame.getCardMarket().sell(positionX,positionY, currentGame.getCurrentPlayer());
        currentGame.getCurrentPlayer().getPlayerBoard().addDevelopmentCard(cardBought, deckIndex);
        currentGame.getCurrentPlayer().setHasDoneMainAction(true);
    }
}
