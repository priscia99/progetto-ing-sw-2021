package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

import java.util.Map;

public class DropLeaderCardAction extends TurnAction{

    public DropLeaderCardAction() {
        this.turnActionType = TurnActionType.DROP_LEADER_CARD;
    }

    public void execute(Game currentGame, Map<String, String> params) {
        super.execute(currentGame);
        //TODO: params:{cardId} che indica la carta che deve scartare
        //ask for leader card to choose
        //once selected drop it and add faith point
    }

    private void onLeaderCardSelected(LeaderCard card){
        currentPlayer.dropLeaderCard(card);
        currentPlayer.addFaithPoints(1);
    }
}
