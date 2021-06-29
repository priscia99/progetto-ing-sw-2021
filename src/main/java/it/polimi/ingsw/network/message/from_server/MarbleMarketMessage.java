package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.market.MarbleMarket;

import java.io.Serializable;

public class MarbleMarketMessage extends Message<ClientController> implements Serializable{

    private static final long serialVersionUID = 28L;
    private final Marble[][] onSale;
    private final Marble notForSale;

    public MarbleMarketMessage(MarbleMarket market) {
        this.onSale = market.getOnSale();
        this.notForSale = market.getNotForSale();
    }

    public void execute(ClientController target) {
        target.getGame().getClientMarbleMarket().refreshMarbleMarket(onSale, notForSale);
        target.viewMarbleMarket();
    }
}
