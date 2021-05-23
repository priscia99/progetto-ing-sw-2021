package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.game.Game;

public class PlayLeaderCardAction extends Action {
    private final String cardId;

    public PlayLeaderCardAction(String id) {
        this.cardId = id;
    }

    public void execute(Game currentGame) {
        currentGame.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().activateLeaderCardById(cardId);
    }
}
