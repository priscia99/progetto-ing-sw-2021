package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

public class PlayLeaderCardAction extends TurnAction{

    public PlayLeaderCardAction() {
        this.turnActionType = TurnActionType.PLAY_LEADER_CARD;
    }

    @Override
    public void execute(Player player, Game currentGame) {
        super.execute(player, currentGame);
        //choose card
    }

    private void onLeaderCardSelected(LeaderCard card){
        currentPlayer.activateLeaderCard(card);
    }
}
