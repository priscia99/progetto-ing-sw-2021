package it.polimi.ingsw.model.player_board.faith_path;

public class PopeFavor {

    private final int first_cell_index;
    private final int points;

    public PopeFavor(int first_cell_index, int points) {
        this.first_cell_index = first_cell_index;
        this.points = points;
    }

    public int getFirstCellIndex() {
        return first_cell_index;
    }
    public int getPoints() {
        return points;
    }

    public void givePoints() {

    }
}
