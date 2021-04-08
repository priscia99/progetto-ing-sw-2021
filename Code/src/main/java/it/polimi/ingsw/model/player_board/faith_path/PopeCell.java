package it.polimi.ingsw.model.player_board.faith_path;

public class PopeCell extends Cell {

    private PopeFavor favor;

    public PopeCell(PopeFavor favor) {
        super();
        this.favor = favor;
    }

    public PopeFavor getFavor() {
        return favor;
    }

    @Override
    public void reach() {
        super.reach();
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
