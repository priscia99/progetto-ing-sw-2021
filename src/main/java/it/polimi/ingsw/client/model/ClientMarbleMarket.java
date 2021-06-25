package it.polimi.ingsw.client.model;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.marble.*;
import it.polimi.ingsw.server.model.market.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ClientMarbleMarket extends Observable<ClientMarbleMarket> implements Serializable {

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


    public void refreshMarbleMarket(Marble[][] onSale, Marble notForSale){
        this.onSale = onSale;
        this.notForSale = notForSale;
    }

    public ArrayList<Marble> getSelectedMarbles(MarbleSelection selection){
        int index = selection.getIndex();
        if (selection.getOrientation().equals(Orientation.HORIZONTAL)) {
            return new ArrayList<>(Arrays.asList(this.onSale[index]));
        } else {
            return Arrays.stream(this.onSale)
                    .map(marbles -> marbles[index])
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    public Marble[][] getOnSale() {
        return onSale;
    }

    public Marble getNotForSale() {
        return notForSale;
    }
}
