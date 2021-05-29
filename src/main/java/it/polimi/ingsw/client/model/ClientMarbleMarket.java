package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.marble.*;
import it.polimi.ingsw.server.model.market.*;

public class ClientMarbleMarket {

    private Marble[][] onSale;
    private Marble notForSale;

    public ClientMarbleMarket() {
        this.onSale = new Marble[0][];
        this.notForSale = null;
    }

    public ClientMarbleMarket(Marble[][] onSale, Marble notForSale) {
        this.onSale = onSale;
        this.notForSale = notForSale;
    }

    public void setOnSale(Marble[][] onSale) {
        this.onSale = onSale;
    }

    public void setNotForSale(Marble notForSale) {
        this.notForSale = notForSale;
    }

    public Marble[][] getOnSale() {
        return onSale;
    }

    public Marble getNotForSale() {
        return notForSale;
    }
}
