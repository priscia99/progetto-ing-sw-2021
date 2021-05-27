package it.polimi.ingsw_old.model.player_board.faith_path;

public class PopeCell extends Cell {

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
        return favor.isUsed() ? favor.getPoints() : 0;
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
