package it.polimi.ingsw.model.player_board.faith_path;

public class EmptyCell extends Cell {

    public EmptyCell() {
        super();
    }

    @Override
    public int reach() {
        super.setReached(true);
        return 0;
    }
}
