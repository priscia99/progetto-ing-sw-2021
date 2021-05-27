package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.card.DevelopmentCard;

import java.io.Serializable;
import java.util.ArrayList;

public class CardsMarketMessage implements Message<ClientController>, Serializable {

    private static final long serialVersionUID = 1L;
    private final ArrayList<DevelopmentCard> developmentCards;

    public CardsMarketMessage(ArrayList<DevelopmentCard> developmentCards) {
        this.developmentCards = developmentCards;
    }

    public void execute(ClientController target) {

    }
}
