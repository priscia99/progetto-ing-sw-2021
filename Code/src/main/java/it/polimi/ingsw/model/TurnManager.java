package it.polimi.ingsw.model;

class TurnManager {
    private Player currentPlayer;
    private boolean mainActionDone;

    public void startTurn(Player player) {
        mainActionDone = false;
        currentPlayer = player;
    }
}