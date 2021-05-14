package it.polimi.ingsw.controller.turn_manager.turn_action;

import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.controller.turn_manager.action_params.DropLeaderCardParams;

public class DropLeaderCardAction extends TurnAction{

    public DropLeaderCardAction() {
        this.turnActionType = TurnActionType.DROP_LEADER_CARD;
    }

    public void execute(Game currentGame, DropLeaderCardParams params) {
        currentGame.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().removeLeaderCardById(params.getCardId());
        currentGame.getCurrentPlayer().addFaithPoints(1);
    }
}
