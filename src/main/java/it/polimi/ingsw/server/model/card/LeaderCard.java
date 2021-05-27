package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.card.effect.Effect;
import it.polimi.ingsw.server.model.card.requirement.Requirement;

/**
 * Extension fo Card to specify leader cards.
 */
public class LeaderCard extends Card {

    private final Effect effect;

    /**
     * Create a LeaderCard object that can add an effect of any kind to a card.
     * @param victoryPoints the points awarded by playing the card
     * @param effect the effect of the card (can be null)
     * @param requirement the requirement to fulfill in order to play the card
     */
    public LeaderCard(int victoryPoints, Effect effect, Requirement requirement) {
        super(victoryPoints, requirement);
        this.effect = effect;
    }

    /**
     *
     * @return the effect of the card.
     */
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
