package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

import java.util.Map;

public class DropLeaderCardAction extends TurnAction{

    public DropLeaderCardAction() {
        this.turnActionType = TurnActionType.DROP_LEADER_CARD;
    }

    public void execute(Game currentGame, Map<String, Object> params) {
        //TODO: move params parsing in controller
        //TODO: use cardId not card
        LeaderCard cardToDrop = (LeaderCard) params.get("cardId");
        currentGame.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().removeLeaderCardWithId(cardToDrop);
        currentGame.getCurrentPlayer().addFaithPoints(1);
    }
}
