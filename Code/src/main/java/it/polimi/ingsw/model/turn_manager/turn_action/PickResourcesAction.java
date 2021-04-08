package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

public class PickResourcesAction extends TurnAction{

    public PickResourcesAction() {
        this.turnActionType = TurnActionType.PICK_RESOURCES;
    }

    @Override
    public TurnActionType getTurnActionType() {
        return super.getTurnActionType();
    }

    @Override
    public void execute(Player player, Game currentGame) {

    }
}