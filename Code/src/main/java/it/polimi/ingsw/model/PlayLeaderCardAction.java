package it.polimi.ingsw.model;
import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.TurnActionType;
import it.polimi.ingsw.model.Player;

public class PlayLeaderCardAction extends TurnAction {
    final TurnActionType turnActionType = TurnActionType.PLAY_LEADER_CARD;

    public PlayLeaderCardAction() {
    }

    @Override
    public void execute(Player currentPlayer, Game currentGame) {

    }
}