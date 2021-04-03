package it.polimi.ingsw.model.marble;

public class MarbleChoice {

    private final Orientation orientation;
    private final int index;

    public MarbleChoice(Orientation orientation, int index) {
        this.orientation = orientation;
        this.index = index;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getIndex() {
        return index;
    }
}
