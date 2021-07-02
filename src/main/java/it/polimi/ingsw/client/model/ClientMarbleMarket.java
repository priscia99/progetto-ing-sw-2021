package it.polimi.ingsw.client.model;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.marble.*;
import it.polimi.ingsw.server.model.market.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ClientMarbleMarket extends Observable<ClientMarbleMarket> implements Serializable {

    private static final long serialVersionUID = 1503L;
    private Marble[][] onSale;
    private Marble notForSale;

    /**
     * Initialize the game marble market with no marbles inside
     */
    public ClientMarbleMarket() {
        this.onSale = new Marble[0][];
        this.notForSale = null;
    }

    /**
     * Initialize the game marble market passing all parameters
     * @param onSale matrix with all currently on sale marbles
     * @param notForSale marble which is currently not for sale
     */
    public ClientMarbleMarket(Marble[][] onSale, Marble notForSale) {
        this.onSale = onSale;
        this.notForSale = notForSale;
    }

    /**
     * Update the status of marble market
     * @param onSale matrix with all currently on sale marbles
     * @param notForSale marble which is currently not for sale
     */
    public void refreshMarbleMarket(Marble[][] onSale, Marble notForSale){
        this.onSale = onSale;
        this.notForSale = notForSale;
    }

    /**
     * Retrives a list of selected marbles based on the player's selection
     * @param selection the marble selection
     * @return list of selected marbles
     */
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

    /**
     * Retrieves the matrix with all currently on sale marbles
     * @return
     */
    public Marble[][] getOnSale() {
        return onSale;
    }

    /**
     * Retrieves the marble which is currently not for sale
     * @return
     */
    public Marble getNotForSale() {
        return notForSale;
    }
}
