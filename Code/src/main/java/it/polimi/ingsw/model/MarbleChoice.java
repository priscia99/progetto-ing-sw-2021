package it.polimi.ingsw.model;

public class MarbleChoice {

    private Orientation orientation;
    private int index;

    public MarbleChoice(Orientation orientation, int index) {
        this.orientation = orientation;
        this.index = index;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
