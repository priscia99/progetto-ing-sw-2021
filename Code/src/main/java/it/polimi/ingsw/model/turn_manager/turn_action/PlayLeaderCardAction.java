package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

import java.util.Map;

public class PlayLeaderCardAction extends TurnAction{

    public PlayLeaderCardAction() {
        this.turnActionType = TurnActionType.PLAY_LEADER_CARD;
    }

    public void execute(Game currentGame, Map<String, Object> params) {
        //TODO: params{cardId} che indica la carta che deve attivate
        //TODO: implement cardId not card
        LeaderCard card = (LeaderCard) params.get("cardId");
        currentGame.getCurrentPlayer().activateLeaderCard(card);
    }
}
