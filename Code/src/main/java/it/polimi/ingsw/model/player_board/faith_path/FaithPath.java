package it.polimi.ingsw.model.player_board.faith_path;

public class FaithPath {

    private final Cell[] cells;
    private int position;
    private int faithPoints;

    public FaithPath(Cell[] cells) {
        this.cells = cells;
        this.position = 0;
        this.faithPoints = 0;
    }

    public Cell[] getCells() {
        return cells;
    }

    public int getPosition() {
        return position;
    }

    public int getFaithPoints() {
        return faithPoints;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setFaithPoints(int faithPoints) {
        this.faithPoints = faithPoints;
    }

    public void goToNextCell() {
        this.position++;
        this.cells[this.position].reach();
    }
}
