package it.polimi.ingsw.model;

abstract class Card {

    private final Effect effect;
    private final int victoryPoints;

    public Card(Effect effect, int victoryPoints) {
        this.effect = effect;
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Effect getEffect() {return effect;}
}
