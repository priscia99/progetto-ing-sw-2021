package it.polimi.ingsw.model;


import src.main.java.it.polimi.ingsw.model.TurnActionType;

abstract class TurnAction {
    private TurnActionType actionType;

    public abstract execute(Player currentPlayer, it.polimi.ingsw.model.Game currentGame);

    public TurnActionType getActionType() {
        return actionType;
    }
}