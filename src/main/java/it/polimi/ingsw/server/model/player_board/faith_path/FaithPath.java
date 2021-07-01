package it.polimi.ingsw.server.model.player_board.faith_path;


import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.data.FaithPathBuilder;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.from_server.FaithPathMessage;
import it.polimi.ingsw.observer.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FaithPath extends Observable<Object>implements Serializable {

    private final Cell[] cells;
    private int faithPoints;

    public FaithPath(Cell[] cells) {
        this.cells = cells;
        this.faithPoints = 0;
    }

    /**
     *
     * @return Faith path structure accordingly to game rules
     */
    public static FaithPath getStandardFaithPath(){
        return new FaithPath(FaithPathBuilder.getPath());
    }


    /**
     *
     * @return a copy of this faith path
     */
    public FaithPath getCopy(){
        FaithPath copy = new FaithPath(this.cells);
        copy.setFaithPoints(this.faithPoints);
        return copy;
    }

    public Cell[] getCells() {
        return cells;
    }

    public int getFaithPoints() {
        return faithPoints;
    }

    public void setFaithPoints(int points){ this.faithPoints = points;}

    /**
     *
     * @return List of acquired pope favours
     */
    public ArrayList<Boolean> getAcquiredPopeFavours(){
        return Arrays.stream(this.getCells())
                .filter(cell -> cell instanceof PopeCell)
                .map(cell -> ((PopeCell) cell).getFavor().isAcquired())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     *
     * @return Victory points produced with this faith path
     */
    public int getVictoryPoints() {
        int points = 0;
        for(int i=0; i<=faithPoints; i++){
            points += cells[i].getVictoryPoints();
        }
        return points;
    }

    /**
     * Activate cell in faith path and increase points
     */
    public void goToNextCell() {
        this.cells[this.faithPoints].reach();
        faithPoints++;
        notify(this);
    }


    /**
     * Check pope favour activation
     * @param index Position to check
     * @return Pope favour activated
     */
    public boolean checkPopeFavor(int index){
        if(!(cells[index] instanceof PopeCell))
            return false;

        PopeFavor favor = ((PopeCell) cells[index]).getFavor();
        if(!favor.isTriggered()){
            if (this.faithPoints >= favor.getFirstCellIndex()){
                favor.setAcquired(true);
                favor.setTriggered(true);
                return true;
            }
            favor.setTriggered(true);
        }
        return false;
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
