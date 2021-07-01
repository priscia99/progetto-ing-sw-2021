package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientCardsMarket;
import it.polimi.ingsw.client.model.ClientDevelopmentCard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.market.CardsMarket;

import java.io.Serializable;
import java.util.ArrayList;

public class CardsMarketMessage extends Message<ClientController> implements Serializable {

    private static final long serialVersionUID = 39L;
    private final ArrayList<ArrayList<ArrayList<ClientDevelopmentCard>>> decks;

    public CardsMarketMessage(CardsMarket market) {
        ClientCardsMarket clientMarket = new ClientCardsMarket(market);
        this.decks = clientMarket.getDecks();
    }

    /**
     * Update card market data on client
     * @param target
     */
    public void execute(ClientController target) {
        target.refreshCardMarket(decks);
    }
}
