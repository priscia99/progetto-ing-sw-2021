package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.requirement.Requirement;

import java.util.UUID;

public abstract class Card {

    private boolean active;
    private final int victoryPoints;
    private final Requirement requirement;
    private final String id;

    public Card(boolean active, int victoryPoints, Requirement requirement) {
        this.id = UUID.randomUUID().toString();
        this.active = active;
        this.victoryPoints = victoryPoints;
        this.requirement = requirement;
    }

    public String getId(){ return id;}

    public Requirement getRequirement(){ return requirement; }

    public boolean isActive() {
        return active;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int play() {
        this.active = true;
        return this.victoryPoints;
    }

    public void setActive(boolean active){ this.active = active; }

    @Override
    public String toString(){
        StringBuilder buffer = new StringBuilder();
        buffer.append("Status: ");
        if(isActive())
            buffer.append("ACTIVE");
        else
            buffer.append("NOT ACTIVE");
        buffer.append("\n");

        buffer.append("Victory points: ").append(victoryPoints).append("\n");
        return buffer.toString();
    }
}
