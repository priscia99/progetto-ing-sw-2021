package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.requirement.Requirement;

public abstract class Card {

    private boolean active;
    private final int victoryPoints;
    private Requirement requirement;

    public Card(boolean active, int victoryPoints, Requirement requirement) {
        this.active = active;
        this.victoryPoints = victoryPoints;
        this.requirement = requirement;
    }

    public void setRequirement(Requirement requirement){ this.requirement = requirement; }

    public Requirement getRequirement(){ return requirement; }

    public boolean isActive() {
        return active;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void play(boolean active) {
        this.active = active;
    }
}
