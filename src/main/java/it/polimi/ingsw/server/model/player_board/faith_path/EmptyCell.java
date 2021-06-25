package it.polimi.ingsw.server.model.player_board.faith_path;

import java.io.Serializable;

public class EmptyCell extends Cell implements Serializable {

    public EmptyCell() {
        super();
    }

    @Override
    public int getVictoryPoints(){
        return 0;
    }

    @Override
    public void reach() {
        super.setReached(true);
    }
}
