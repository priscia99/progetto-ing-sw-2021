package it.polimi.ingsw.server.model.marble;

import javax.sql.rowset.serial.SerialStruct;
import java.io.Serializable;

/**
 * Class that models the selections in the MarbleMarket.
 */
public class MarbleSelection implements Serializable {

    private final Orientation orientation;
    private final int index;

    /**
     * Create a MarbleSelection object that contains the orientation and the index of the selection.
     * @param orientation
     * @param index
     */
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
