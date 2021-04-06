package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.effect.Effect;
import it.polimi.ingsw.model.card.requirement.Requirement;

public class LeaderCard extends Card{

    private final Effect effect;

    public LeaderCard(boolean active, int victoryPoints, Effect effect, Requirement requirement) {
        super(active, victoryPoints, requirement);
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }

    @Override
    public boolean isActive() {
        return super.isActive();
    }

    @Override
    public int getVictoryPoints() {
        return super.getVictoryPoints();
    }

    @Override
    public void play(boolean active) {
        super.play(active);
    }

    @Override
    public String toString(){
        return super.toString() +
                getRequirement().toString() +
                getEffect().toString() +
                "\n";
    }
}
