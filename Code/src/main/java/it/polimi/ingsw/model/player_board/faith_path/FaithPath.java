package it.polimi.ingsw.model.player_board.faith_path;

import it.polimi.ingsw.data.FaithPathBuilder;

public class FaithPath {

    private final Cell[] cells;
    private int position;
    private int faithPoints;

    public FaithPath(Cell[] cells) {
        this.cells = cells;
        this.position = 0;
        this.faithPoints = 0;
    }

    public static FaithPath getStandardFaithPath(){
        return new FaithPath(FaithPathBuilder.getPath());
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

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<cells.length; i++){
            builder.append("Index: ").append((i+1)).append("\n");
            builder.append(cells[i].toString()).append("\n");
        }
        return builder.toString();
    }
}
