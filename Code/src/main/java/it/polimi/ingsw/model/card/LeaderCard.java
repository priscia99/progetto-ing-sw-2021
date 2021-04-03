package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.effect.Effect;

public class LeaderCard extends Card{

    private final Effect effect;

    public LeaderCard(boolean active, int victoryPoints, Effect effect) {
        super(active, victoryPoints);
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
    public void setActive(boolean active) {
        super.setActive(active);
    }
}
