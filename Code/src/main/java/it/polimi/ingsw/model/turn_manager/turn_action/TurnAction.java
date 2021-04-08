package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

public abstract class TurnAction {

    protected TurnActionType turnActionType;

    public TurnAction() {
    }


    public TurnActionType getTurnActionType() {
        return turnActionType;
    }

    public abstract void execute(Player player, Game currentGame);
}
