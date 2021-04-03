package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.marble.Marble;

import java.util.List;

public class MarbleMarket {

    private Marble[][] onSale;
    private Marble notForSale;

    public MarbleMarket(Marble[][] onSale, Marble notForSale) {
        this.onSale = onSale;
        this.notForSale = notForSale;
    }

    public Marble[][] getOnSale() {
        return onSale;
    }

    public void setOnSale(Marble[][] onSale) {
        this.onSale = onSale;
    }

    public Marble getNotForSale() {
        return notForSale;
    }

    public void setNotForSale(Marble notForSale) {
        this.notForSale = notForSale;
    }

    public void sell() {

    }

    public void renew() {

    }
}
