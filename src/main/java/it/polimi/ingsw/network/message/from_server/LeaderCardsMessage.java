package it.polimi.ingsw.network.message.from_server;


import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.card.LeaderCard;

import java.io.Serializable;
import java.util.ArrayList;

public class LeaderCardsMessage implements Message<ClientController>, Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<LeaderCard> leaderCards;

    public LeaderCardsMessage(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    public void execute(ClientController controller) {

    }
}
