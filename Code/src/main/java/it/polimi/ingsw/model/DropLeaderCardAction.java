package it.polimi.ingsw.model;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.TurnActionType;
import it.polimi.ingsw.model.Player;

public class DropLeaderCardAction extends TurnAction {
    final TurnActionType turnActionType = TurnActionType.DROP_LEADER_CARD;

    public DropLeaderCardAction() {
    }

    @Override
    public void execute(Player currentPlayer, Game currentGame) {

    }
}