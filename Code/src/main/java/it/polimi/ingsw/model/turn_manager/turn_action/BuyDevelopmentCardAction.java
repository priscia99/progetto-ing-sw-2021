package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

public class BuyDevelopmentCardAction extends TurnAction{

    public BuyDevelopmentCardAction() {
        this.turnActionType = TurnActionType.BUY_DEVELOPMENT_CARD;
    }

    @Override
    public TurnActionType getTurnActionType() {
        return super.getTurnActionType();
    }

    @Override
    public void execute(Player player, Game currentGame) {

    }
}
