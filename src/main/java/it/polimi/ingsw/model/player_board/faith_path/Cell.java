package it.polimi.ingsw.model.player_board.faith_path;

public abstract class Cell {

    private boolean reachedUp;

    public Cell() {
        this.reachedUp = false;
    }

    public void reach(){this.reachedUp = true;};

    public void setReached(boolean reached){ reachedUp = reached; }

    public abstract int getVictoryPoints();

    @Override
    public String toString(){
        return "Reached: " + reachedUp + "\n";
    }
}
