package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.effect.Effect;
import it.polimi.ingsw.model.card.requirement.Requirement;
import it.polimi.ingsw.model.locally_copiable.LocallyCopyable;

public class LeaderCard extends Card implements LocallyCopyable<LeaderCard.LeaderCardLocalCopy> {

    private final Effect effect;

    public static class LeaderCardLocalCopy {

        private final Effect effect;
        private final boolean active;
        private final int victoryPoints;
        private final Requirement requirement;

        public LeaderCardLocalCopy(Effect effect, boolean active, int victoryPoints, Requirement requirement) {
            this.effect = effect;
            this.active = active;
            this.victoryPoints = victoryPoints;
            this.requirement = requirement;
        }

        public Effect getEffect() {
            return effect;
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
    public LeaderCardLocalCopy getLocalCopy() {
        return new LeaderCardLocalCopy(this.effect, this.isActive(), this.getVictoryPoints(), this.getRequirement());
    }

    public LeaderCard(boolean active, int victoryPoints, Effect effect, Requirement requirement) {
        super(active, victoryPoints, requirement);
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }

    @Override
    public String toString(){
        return super.toString() +
                getRequirement().toString() +
                getEffect().toString() +
                "\n";
    }
}
