package it.polimi.ingsw.model;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.TurnActionType;
import it.polimi.ingsw.model.Player;

public class StartProductionAction extends TurnAction {
    final TurnActionType turnActionType = TurnActionType.START_PRODUCTION;

    @Override
    public void execute(Player currentPlayer, Game currentGame) {

    }
}
