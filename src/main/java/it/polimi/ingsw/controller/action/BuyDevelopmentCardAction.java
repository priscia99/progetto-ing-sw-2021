package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.GameController;
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

    public void execute(GameController controller){
        controller.doActionA(positionX, positionY, deckIndex);
    }
}
