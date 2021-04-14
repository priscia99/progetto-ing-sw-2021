package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

import java.util.Map;

public abstract class TurnAction {

    protected TurnActionType turnActionType;
    public TurnAction() {
    }


    public TurnActionType getTurnActionType() {
        return turnActionType;
    }

    abstract public void execute(Game currentGame, Map<String, Object> params);
}
