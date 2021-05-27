package it.polimi.ingsw_old.view.client_model;

import it.polimi.ingsw_old.model.marble.Marble;
import it.polimi.ingsw_old.model.market.MarbleMarket;

public class ClientMarbleMarket {

    private final Marble[][] onSale;
    private final Marble notForSale;

    public ClientMarbleMarket(MarbleMarket marbleMarket) {
        this.onSale = marbleMarket.getOnSale();
        this.notForSale = marbleMarket.getNotForSale();
    }

    public ClientMarbleMarket(Marble[][] onSale, Marble notForSale) {
        this.onSale = onSale;
        this.notForSale = notForSale;
    }

    public Marble[][] getOnSale() {
        return onSale;
    }

    public Marble getNotForSale() {
        return notForSale;
    }
}
