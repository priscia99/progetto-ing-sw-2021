package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.marble.Marble;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.*;

public class MarbleMarket {

    private Marble[][] onSale;
    private Marble notForSale;

    static public MarbleMarket getStartingMarket(){
        List<Marble> marbles = new ArrayList<>();
        //TODO: complete with correct marbles
        marbles.addAll(0, Arrays.asList(
                new Marble(ResourceType.COIN),
                new Marble(ResourceType.FAITH),
                new Marble(ResourceType.SERVANT),
                new Marble(ResourceType.COIN),
                new Marble(ResourceType.FAITH),
                new Marble(ResourceType.SERVANT),
                new Marble(ResourceType.COIN),
                new Marble(ResourceType.FAITH),
                new Marble(ResourceType.SERVANT),
                new Marble(ResourceType.COIN),
                new Marble(ResourceType.FAITH),
                new Marble(ResourceType.SERVANT),
                new Marble(ResourceType.SERVANT)
                ));
        Collections.shuffle(marbles);
        Marble[][] initialMarbles = new Marble[4][3];
        for(int i = 0; i<4; i++){
            for(int j = 0; j<3; j++){
                initialMarbles[i][j] = marbles.get(i+j);
            }
        }
        Marble notForSaleMarble = marbles.get(marbles.size()-1);
        return new MarbleMarket(initialMarbles, notForSaleMarble);
    }

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
