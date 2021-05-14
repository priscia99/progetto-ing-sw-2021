package it.polimi.ingsw.controller.turn_manager.turn_action;

import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.controller.turn_manager.action_params.PlayLeaderCardParams;

public class PlayLeaderCardAction extends TurnAction{

    public PlayLeaderCardAction() {
        this.turnActionType = TurnActionType.PLAY_LEADER_CARD;
    }

    public void execute(Game currentGame, PlayLeaderCardParams params) {
        currentGame.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().activateLeaderCardById(params.getCardId());
    }
}
