package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.card.requirement.Requirement;

public class DevelopmentCard extends Card{

    private final int level;
    private final ProductionEffect effect;
    private final Color color;

    public DevelopmentCard(boolean active, int victoryPoints, int level, ProductionEffect effect, Color color, Requirement requirement) {
        super(active, victoryPoints, requirement);
        this.level = level;
        this.effect = effect;
        this.color = color;
    }

    public Color getColor() {
        return color;
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
    public void play(boolean active) {
        super.play(active);
    }
}
