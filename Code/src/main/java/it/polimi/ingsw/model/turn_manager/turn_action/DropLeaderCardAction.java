package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.turn_manager.action_params.DropLeaderCardParams;

import java.util.Map;

public class DropLeaderCardAction extends TurnAction{

    public DropLeaderCardAction() {
        this.turnActionType = TurnActionType.DROP_LEADER_CARD;
    }

    public void execute(Game currentGame, DropLeaderCardParams params) {
        currentGame.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().removeLeaderCardWithId(params.getCardId());
        currentGame.getCurrentPlayer().addFaithPoints(1);
    }
}
