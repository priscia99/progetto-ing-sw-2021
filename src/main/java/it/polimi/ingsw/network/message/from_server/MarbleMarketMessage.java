package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.marble.Marble;

import java.io.Serializable;

public class MarbleMarketMessage extends Message<ClientController> implements Serializable{

    private static final long serialVersionUID = 1L;
    private final Marble[][] onSale;
    private final Marble notForSale;

    public MarbleMarketMessage(Marble[][] onSale, Marble notForSale) {
        this.onSale = onSale;
        this.notForSale = notForSale;
    }

    public void execute(ClientController target) {

    }
}
