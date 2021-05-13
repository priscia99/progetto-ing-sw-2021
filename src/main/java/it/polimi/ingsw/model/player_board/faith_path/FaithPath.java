package it.polimi.ingsw.model.player_board.faith_path;

import it.polimi.ingsw.data.FaithPathBuilder;
import it.polimi.ingsw.network.observer.Observable;

public class FaithPath extends Observable<Integer> {

    private final Cell[] cells;
    private int faithPoints;

    public FaithPath(Cell[] cells) {
        this.cells = cells;
        this.faithPoints = 0;
    }

    public static FaithPath getStandardFaithPath(){
        return new FaithPath(FaithPathBuilder.getPath());
    }

    public Cell[] getCells() {
        return cells;
    }

    public int getFaithPoints() {
        return faithPoints;
    }

    public int getVictoryPoints() {
        int points = 0;
        for(int i=0; i<=faithPoints; i++){
            points += cells[i].getVictoryPoints();
        }
        return points;
    }

    public void goToNextCell() {
        this.cells[this.faithPoints].reach();
        faithPoints++;

        notify(this.faithPoints);
    }

    // TODO: This function needs to be called by an observer
    /**
     * Triggered by an observer, this method check if player is eligible to receive the points given by a pope favor
     * of a pope cell.
     * @param index Index of a pope cell.
     * @throws IllegalArgumentException Indicates if the given index doesn't refer to a pope cell.
     */
    public void checkPopeFavor(int index){
        if(!(cells[index] instanceof PopeCell))
            throw new IllegalArgumentException("This position doesn't refer to a pope cell.");

        PopeFavor favor = ((PopeCell) cells[index]).getFavor();
        if(!favor.isUsed()){
            // Favor was not triggered before
            if (this.faithPoints >= favor.getFirstCellIndex()){
                favor.setUsed(true);
            }
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
