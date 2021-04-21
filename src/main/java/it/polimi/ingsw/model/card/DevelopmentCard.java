package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.card.requirement.Requirement;
import it.polimi.ingsw.model.locally_copiable.LocallyCopyable;

public class DevelopmentCard extends Card {

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
    public String toString(){
        return super.toString() + "Level: " + getLevel() + "\nColor: " + getColor().name() + "\n" +
                getRequirement().toString() +
                getEffect().toString() +
                "\n";
    }
}
