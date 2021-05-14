package it.polimi.ingsw.server_model.player_board.faith_path;

public class PopeFavor {

    private final int first_cell_index;
    private final int points;
    private boolean used;

    public PopeFavor(int first_cell_index, int points) {
        this.first_cell_index = first_cell_index;
        this.points = points;
        this.used = false;
    }

    public boolean isUsed(){ return used; }

    public void setUsed(boolean used){ this.used = used; }

    public int getFirstCellIndex() {
        return first_cell_index;
    }

    public int getPoints() {
        return points;
    }

}
