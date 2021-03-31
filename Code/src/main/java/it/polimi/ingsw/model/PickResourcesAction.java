package it.polimi.ingsw.model;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.TurnActionType;
import it.polimi.ingsw.model.Player;

public class PickResourcesAction extends TurnAction {
    final TurnActionType turnActionType = TurnActionType.PICK_RESOURCES;

    public PickResourcesAction() {
    }

    @Override
    public void execute(Player currentPlayer, Game currentGame) {

    }
}
