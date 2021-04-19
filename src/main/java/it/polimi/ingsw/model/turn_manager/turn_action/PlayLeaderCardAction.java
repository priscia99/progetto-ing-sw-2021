package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.turn_manager.action_params.PlayLeaderCardParams;

import java.util.Map;

public class PlayLeaderCardAction extends TurnAction{

    public PlayLeaderCardAction() {
        this.turnActionType = TurnActionType.PLAY_LEADER_CARD;
    }

    public void execute(Game currentGame, PlayLeaderCardParams params) {
        currentGame.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().activateWithId(params.getCardId());
    }
}
