package it.polimi.ingsw_old.model.marble;

public class MarbleSelection {

    private final Orientation orientation;
    private final int index;

    public MarbleSelection(Orientation orientation, int index) {
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
