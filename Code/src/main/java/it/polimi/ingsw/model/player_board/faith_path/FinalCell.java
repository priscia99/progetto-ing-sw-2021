package it.polimi.ingsw.model.player_board.faith_path;

public class FinalCell extends Cell{
    private final int points;
    private PopeFavor favor;

    public FinalCell(int points, PopeFavor favor) {
        this.points = points;
        this.favor = favor;
    }

    public int getPoints(){ return points; }
    public PopeFavor getFavor(){ return favor; }

    @Override
    public String toString(){
        return super.toString() +
                "Type: " + "FINAL CELL" + "\n" +
                "Victory points at reaching: " + points + "\n" +
                "Favor: \n" +
                "\tStarting index: " + favor.getFirstCellIndex() + "\n" +
                "\tVictory points given: " + favor.getPoints() + "\n";
    }

}
