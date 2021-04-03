package it.polimi.ingsw.model.turn_manager;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

public class TurnManager {

    private boolean mainActionDone;
    private Player player;
    private final Game game;

    public TurnManager(boolean mainActionDone, Player player, Game game) {
        this.mainActionDone = mainActionDone;
        this.player = player;
        this.game = game;
    }

    public boolean isMainActionDone() {
        return mainActionDone;
    }

    public void setMainActionDone(boolean mainActionDone) {
        this.mainActionDone = mainActionDone;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void startTurn(Player player) {

    }
}
