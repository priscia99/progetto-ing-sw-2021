package it.polimi.ingsw.model.turn_manager;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.utils.CustomLogger;

public class TurnManager {

    private boolean mainActionDone;
    private Player currentPlayer;
    private final Game game;

    public TurnManager(Game game) {
        this.game = game;
    }

    public boolean isMainActionDone() {
        return mainActionDone;
    }

    public void setMainActionDone(boolean mainActionDone) {
        this.mainActionDone = mainActionDone;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    public Game getGame() {
        return game;
    }

    public void startTurn(Player player) {
        this.currentPlayer = player;
        CustomLogger.getLogger().info(String.format("%s has started the turn.", currentPlayer.getNickname()));
    }
}
