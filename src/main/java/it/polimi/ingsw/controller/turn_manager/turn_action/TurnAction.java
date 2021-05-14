package it.polimi.ingsw.controller.turn_manager.turn_action;

public abstract class TurnAction {

    protected TurnActionType turnActionType;
    public TurnAction() {
    }
    public TurnActionType getTurnActionType() {
        return turnActionType;
    }
}
