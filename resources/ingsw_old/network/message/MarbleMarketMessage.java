package it.polimi.ingsw_old.network.message;

import it.polimi.ingsw_old.controller.Controller;
import it.polimi.ingsw_old.model.marble.Marble;

import java.io.Serializable;

public class MarbleMarketMessage implements Message, Serializable {

    private final Marble[][] onSale;
    private final Marble notForSale;

    public MarbleMarketMessage(Marble[][] onSale, Marble notForSale) {
        this.onSale = onSale;
        this.notForSale = notForSale;
    }

    @Override
    public void execute(Controller controller) {

    }
}
