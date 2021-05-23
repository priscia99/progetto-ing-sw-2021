package v2.server.model.player_board.faith_path;

public class EmptyCell extends Cell {

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
