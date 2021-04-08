package it.polimi.ingsw.model.player_board.faith_path;

public class PointsCell extends Cell{

    private final int points;

    public PointsCell(int points) {
        super();
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public int reach() {
        super.setReached(true);
        return getPoints();
    }

    @Override
    public String toString(){
        return super.toString() +
                "Type: " + "POINTS CELL" + "\n" +
                "Victory points at reaching: " + points + "\n";
    }
}
