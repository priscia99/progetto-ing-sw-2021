package it.polimi.ingsw.server.model.market;

import it.polimi.ingsw.data.MarbleMarketBuilder;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.marble.Orientation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MarbleMarket extends Observable<MarbleMarket> {

    // 4 columns
    // 3 rows
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

    public Marble getNotForSale() {
        return notForSale;
    }

    public ArrayList<Marble> sell(MarbleSelection selection) {
        if (selection.getOrientation().equals(Orientation.HORIZONTAL)) {
            int index = selection.getIndex();

            // copy marbles to return
            ArrayList<Marble> soldMarbles = new ArrayList<Marble>(Arrays.asList(this.onSale[index]));

            // renew marbles
            Marble tempNotForSale = this.onSale[index][0];
            //noinspection SuspiciousSystemArraycopy
            System.arraycopy(this.onSale[index], 1, this.onSale[index], 0, 3);
            this.onSale[index][3] = this.notForSale;
            this.notForSale = tempNotForSale;

            notify(this);

            return soldMarbles;

        } else if (selection.getOrientation().equals(Orientation.VERTICAL)) {
            int index = selection.getIndex();

            // copy marbles to return
            ArrayList<Marble> soldMarbles = Arrays.stream(this.onSale)
                    .map(marbles -> marbles[index])
                    .collect(Collectors.toCollection(ArrayList::new));

            // renew marbles
            Marble tempNotForSale = this.onSale[0][index];
            for (int i=0; i<2; i++) {
                this.onSale[i][index] = this.onSale[i+1][index];
            }
            this.onSale[2][index] = this.notForSale;
            this.notForSale = tempNotForSale;

            notify(this);

            return soldMarbles;

        } else {
            throw new IllegalArgumentException(
                    "selection.getOrientation() did not return Orientation.HORIZONTAL/VERTICAL");
        }
    }
}
