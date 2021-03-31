package it.polimi.ingsw.model;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.TurnAction;
import it.polimi.ingsw.model.TurnActionType;
import it.polimi.ingsw.model.Player;

public class BuyDevelopmentCardAction extends it.polimi.ingsw.model.TurnAction {
    final TurnActionType turnActionType = TurnActionType.BUY_DEVELOPMENT_CARD;

    public BuyDevelopmentCardAction() {
    }

    @Override
    public void execute(Player currentPlayer, Game currentGame) {

    }
}
