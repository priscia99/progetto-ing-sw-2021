package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

import java.util.Map;

public class BuyDevelopmentCardAction extends TurnAction{

    public BuyDevelopmentCardAction() {
        this.turnActionType = TurnActionType.BUY_DEVELOPMENT_CARD;
    }

    public void execute(Game currentGame, Map<String, String> params) {
        super.execute(currentGame);
        //TODO: params:{x,y} che indica la posizione della carta comprata

    }

    private void onDevelopmentCardSelected(DevelopmentCard card, int deckIndex){
        //TODO: rimuovere la carta dal mercato
        currentPlayer.getPlayerBoard().addDevelopmentCard(card, deckIndex);
    }
}
