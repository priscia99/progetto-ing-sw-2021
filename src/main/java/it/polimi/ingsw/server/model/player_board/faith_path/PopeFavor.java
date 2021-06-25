package it.polimi.ingsw.server.model.player_board.faith_path;

import java.io.Serializable;

public class PopeFavor implements Serializable {

    private final int first_cell_index;
    private final int points;
    private boolean acquired;
    private boolean triggered;

    public PopeFavor(int first_cell_index, int points) {
        this.first_cell_index = first_cell_index;
        this.points = points;
        this.triggered = false;
        this.acquired = false;
    }

    public boolean isTriggered(){ return triggered; }

    public boolean isAcquired(){return acquired;}

    public void setAcquired(boolean acquired){this.acquired = acquired;}

    public void setTriggered(boolean triggered){ this.triggered = triggered; }

    public int getFirstCellIndex() {
        return first_cell_index;
    }

    public int getPoints() {
        return points;
    }

}
