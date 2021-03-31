package it.polimi.ingsw.model;

public class DevelopmentCard extends Card{

    private final int level;
    private final Color color;

    public DevelopmentCard(Effect effect, int victoryPoints, int level, Color color) {
        super(effect, victoryPoints);
        this.level = level;
        this.color = color;
    }

    @Override
    public int getVictoryPoints() {
        return super.getVictoryPoints();
    }

    @Override
    public Effect getEffect() {
        return super.getEffect();
    }

    public int getLevel() {
        return level;
    }

    public Color getColor() {
        return color;
    }
}
