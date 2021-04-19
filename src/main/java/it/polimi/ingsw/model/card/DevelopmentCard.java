package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.card.requirement.Requirement;
import it.polimi.ingsw.model.locally_copiable.LocallyCopyable;

public class DevelopmentCard extends Card implements LocallyCopyable<DevelopmentCard.DevelopmentCardLocalCopy> {

    private final int level;
    private final ProductionEffect effect;
    private final Color color;

    public static class DevelopmentCardLocalCopy {

        private final int level;
        private final ProductionEffect effect;
        private final Color color;
        private final boolean active;
        private final int victoryPoints;
        private final Requirement requirement;

        public DevelopmentCardLocalCopy(
                int level,
                ProductionEffect effect,
                Color color,
                boolean active,
                int victoryPoints,
                Requirement requirement) {
            this.level = level;
            this.effect = effect;
            this.color = color;
            this.active = active;
            this.victoryPoints = victoryPoints;
            this.requirement = requirement;
        }

        public int getLevel() {
            return level;
        }

        public ProductionEffect getEffect() {
            return effect;
        }

        public Color getColor() {
            return color;
        }

        public boolean isActive() {
            return active;
        }

        public int getVictoryPoints() {
            return victoryPoints;
        }

        public Requirement getRequirement() {
            return requirement;
        }
    }

    @Override
    public DevelopmentCardLocalCopy getLocalCopy() {
        return new DevelopmentCardLocalCopy(
                this.level,
                this.effect,
                this.color,
                this.isActive(),
                this.getVictoryPoints(),
                this.getRequirement()
        );
    }

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
