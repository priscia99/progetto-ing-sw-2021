package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.card.requirement.Requirement;

/**
 * Extension of Card to specify cards with a development effect that can be purchased in card market.
 */
public class DevelopmentCard extends Card {

    private final int level;
    private final ProductionEffect effect;
    private final Color color;

    /**
     * Create a card object decorated with development cards specific properties.
     * @param victoryPoints victory points awared by playing the card
     * @param level level of the card (1, 2, 3)
     * @param effect production effect of the card
     * @param color color of the card (PURPLE, GREEN, YELLOW, BLUE)
     * @param requirement cost of the card
     */
    public DevelopmentCard(int victoryPoints, int level, ProductionEffect effect, Color color, Requirement requirement) {
        super(victoryPoints, requirement);
        this.level = level;
        this.effect = effect;
        this.color = color;
    }

    /**
     *
     * @return the color of the card.
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @return the level of the card.
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @return the production effect of the card.
     */
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
