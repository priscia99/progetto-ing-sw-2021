package it.polimi.ingsw.model;
import it.polimi.ingsw.model.TurnActionType;

public abstract class TurnAction {
    private TurnActionType actionType;

    abstract public void execute(Player currentPlayer, it.polimi.ingsw.model.Game currentGame);

    public TurnActionType getActionType() {
        return actionType;
    }
}