package it.polimi.ingsw.model.card;

public abstract class Card {

    private boolean active;
    private final int victoryPoints;

    public Card(boolean active, int victoryPoints) {
        this.active = active;
        this.victoryPoints = victoryPoints;
    }

    public boolean isActive() {
        return active;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
