package it.polimi.ingsw.model.market;

import it.polimi.ingsw.data.MarbleMarketBuilder;
import it.polimi.ingsw.model.marble.Marble;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.*;

public class MarbleMarket {

    private Marble[][] onSale;
    private Marble notForSale;

    static public MarbleMarket getStartingMarket(){
        MarbleMarketBuilder builder = new MarbleMarketBuilder();
        return new MarbleMarket(builder.getMarket(), builder.getNotForSale());
    }

    public MarbleMarket(Marble[][] onSale, Marble notForSale) {
        this.onSale = onSale;
        this.notForSale = notForSale;
    }

    public Marble[][] getOnSale() {
        return onSale;
    }

    private void setOnSale(Marble[][] onSale) {
        this.onSale = onSale;
    }

    public Marble getNotForSale() {
        return notForSale;
    }

    private void setNotForSale(Marble notForSale) {
        this.notForSale = notForSale;
    }

    // TODO: fill the functions
    public void sell() {

    }

    public void renew() {

    }
}
