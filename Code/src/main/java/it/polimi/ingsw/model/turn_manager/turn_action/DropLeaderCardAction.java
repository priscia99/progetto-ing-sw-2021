package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

public class DropLeaderCardAction extends TurnAction{

    public DropLeaderCardAction() {
        this.turnActionType = TurnActionType.DROP_LEADER_CARD;
    }

    @Override
    public TurnActionType getTurnActionType() {
        return super.getTurnActionType();
    }

    @Override
    public void execute(Player player, Game currentGame) {

    }
}
