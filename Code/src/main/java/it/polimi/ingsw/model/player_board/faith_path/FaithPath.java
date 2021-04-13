package it.polimi.ingsw.model.player_board.faith_path;

import it.polimi.ingsw.data.FaithPathBuilder;
import it.polimi.ingsw.observer.Observable;

public class FaithPath extends Observable<Integer> {

    private final Cell[] cells;
    private int position; // TODO remove
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

        notify(this.position);
    }

    public void setFaithPoints(int faithPoints) {
        this.faithPoints = faithPoints;

        notify(this.position);
    }

    public void goToNextCell() {
        this.position++;
        faithPoints += this.cells[this.position-1].reach();

        notify(this.position);
    }

    // TODO: This function needs to be called by an observer
    /**
     * Triggered by an observer, this method check if player is eligible to receive the points given by a pope favor
     * of a pope cell.
     * @param index Index of a pope cell.
     * @throws IllegalArgumentException Indicates if the given index doesn't refer to a pope cell.
     */
    public void checkPopeFavor(int index){
        if(!(cells[index-1] instanceof PopeCell))
            throw new IllegalArgumentException("This position doesn't refer to a pope cell.");

        PopeFavor favor = ((PopeCell) cells[index-1]).getFavor();
        if(!favor.isUsed()){
            // Favor was not triggered before
            if (this.position >= favor.getFirstCellIndex()){
                this.faithPoints += favor.getPoints();
            }
            favor.setUsed(true);
        }
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
