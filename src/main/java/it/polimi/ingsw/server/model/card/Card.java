package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.card.requirement.Requirement;

import java.util.UUID;

/**
 * Abstract class to model cards objects that can be played to use their effects and properties.
 */
public abstract class Card {

    private boolean active;
    private final int victoryPoints;
    private final Requirement requirement;
    private final String id;

    /**
     * Create an inactive Card object to use in an extension class.
     * @param victoryPoints the victory points rewarded by playing the card
     * @param requirement the requirement to fulfill in order to play/buy the card
     */
    public Card(int victoryPoints, Requirement requirement) {
        // FIXME: dangerous UUID
        this.id = UUID.randomUUID().toString();
        this.active = false;
        this.victoryPoints = victoryPoints;
        this.requirement = requirement;
    }

    /**
     *
     * @return the unique id of the card.
     */
    public String getId(){ return id;}

    /**
     *
     * @return the requirement associated to the card.
     */
    public Requirement getRequirement(){ return requirement; }

    /**
     * Test if the card is active.
     * @return true if the card is active, false if not.
     */
    public boolean isActive() {
        return active;
    }

    /**
     *
     * @return the victory points given when the card is played.
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Play (activate) the card.
     * @return the victory points when the card is played.
     */
    public int play() {
        this.active = true;
        return this.victoryPoints;
    }

    /**
     * Manual activation/inactivation of the card.
     * @param active true to activate the card, false to inactivate it
     */
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
