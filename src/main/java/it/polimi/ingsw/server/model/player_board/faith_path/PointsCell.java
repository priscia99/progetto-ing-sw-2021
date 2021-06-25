package it.polimi.ingsw.server.model.player_board.faith_path;

import java.io.Serializable;

public class PointsCell extends Cell implements Serializable {

    private final int points;

    public PointsCell(int points) {
        super();
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public void reach() {
        super.setReached(true);
    }

    @Override
    public int getVictoryPoints() {
        return points;
    }

    @Override
    public String toString(){
        return super.toString() +
                "Type: " + "POINTS CELL" + "\n" +
                "Victory points at reaching: " + points + "\n";
    }
}
