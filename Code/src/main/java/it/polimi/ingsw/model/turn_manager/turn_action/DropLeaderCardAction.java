package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

public class DropLeaderCardAction extends TurnAction{

    public DropLeaderCardAction() {
        this.turnActionType = TurnActionType.DROP_LEADER_CARD;
    }

    @Override
    public void execute(Player player, Game currentGame) {
        super.execute(player, currentGame);
        //ask for leader card to choose
        //once selected drop it and add faith point
    }

    private void onLeaderCardSelected(LeaderCard card){
        currentPlayer.dropLeaderCard(card);
        currentPlayer.addFaithPoints(1);
    }
}
