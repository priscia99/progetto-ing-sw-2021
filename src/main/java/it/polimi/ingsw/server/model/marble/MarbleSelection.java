package it.polimi.ingsw.server.model.marble;

import javax.sql.rowset.serial.SerialStruct;
import java.io.Serializable;

public class MarbleSelection implements Serializable {

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
