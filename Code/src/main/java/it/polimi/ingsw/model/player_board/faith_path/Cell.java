package it.polimi.ingsw.model.player_board.faith_path;

public abstract class Cell {

    private boolean reachedUp;

    public Cell() {
        this.reachedUp = false;
    }

    public abstract int reach();

    public void setReached(boolean reached){ reachedUp = reached; }

    @Override
    public String toString(){
        return "Reached: " + reachedUp + "\n";
    }
}
