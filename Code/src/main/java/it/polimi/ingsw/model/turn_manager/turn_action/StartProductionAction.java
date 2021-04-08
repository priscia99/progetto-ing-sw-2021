package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

public class StartProductionAction extends TurnAction{

    public StartProductionAction() {
        this.turnActionType = TurnActionType.START_PRODUCTION;
    }

    @Override
    public TurnActionType getTurnActionType() {
        return super.getTurnActionType();
    }

    @Override
    public void execute(Player player, Game currentGame) {

    }
}
