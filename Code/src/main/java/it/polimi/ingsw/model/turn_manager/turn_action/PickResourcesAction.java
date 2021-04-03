package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

public class PickResourcesAction extends TurnAction{

    public PickResourcesAction(Game game, TurnActionType turnActionType) {
        super(game, turnActionType);
    }

    @Override
    public Game getGame() {
        return super.getGame();
    }

    @Override
    public TurnActionType getTurnActionType() {
        return super.getTurnActionType();
    }

    @Override
    public void execute(Player player, Game currentGame) {

    }
}