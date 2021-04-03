package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

public abstract class TurnAction {

    private final Game game;
    private final TurnActionType turnActionType;

    public TurnAction(Game game, TurnActionType turnActionType) {
        this.game = game;
        this.turnActionType = turnActionType;
    }

    public Game getGame() {
        return game;
    }

    public TurnActionType getTurnActionType() {
        return turnActionType;
    }

    public abstract void execute(Player player, Game currentGame);
}
