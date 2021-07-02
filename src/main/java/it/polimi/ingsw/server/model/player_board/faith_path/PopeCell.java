package it.polimi.ingsw.server.model.player_board.faith_path;

import java.io.Serializable;

public class PopeCell extends Cell implements Serializable {

    private static final long serialVersionUID = 1005L;
    private PopeFavor favor;
    private int points;

    public PopeCell(PopeFavor favor, int points) {
        super();
        this.favor = favor;
        this.points = points;
    }

    public PopeFavor getFavor() {
        return favor;
    }

    @Override
    public int getVictoryPoints() {
        return favor.isAcquired() ? favor.getPoints() : 0;
    }

    @Override
    public String toString(){
        return super.toString() +
                "Type: " + "POPE CELL" + "\n" +
                "Favor: \n" +
                "\tStarting index: " + favor.getFirstCellIndex() + "\n" +
                "\tVictory points given: " + favor.getPoints() + "\n";
    }
}
