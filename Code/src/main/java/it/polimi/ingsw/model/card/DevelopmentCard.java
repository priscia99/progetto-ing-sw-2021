package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.effect.ProductionEffect;

public class DevelopmentCard extends Card{

    private final int level;
    private final ProductionEffect effect;

    public DevelopmentCard(boolean active, int victoryPoints, int level, ProductionEffect effect) {
        super(active, victoryPoints);
        this.level = level;
        this.effect = effect;
    }

    public int getLevel() {
        return level;
    }

    public ProductionEffect getEffect() {
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
