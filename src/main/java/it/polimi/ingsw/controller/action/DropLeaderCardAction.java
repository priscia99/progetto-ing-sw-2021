package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.server_model.game.Game;

public class DropLeaderCardAction extends Action {
    private final String cardId;

    public DropLeaderCardAction(String id) {
        this.cardId = id;
    }

    public void execute(Game currentGame) {
        currentGame.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().removeLeaderCardById(cardId);
        currentGame.getCurrentPlayer().addFaithPoints(1);
    }
}
