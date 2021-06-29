package it.polimi.ingsw.network.message.from_server;


import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.card.LeaderCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class LeaderCardsMessage extends Message<ClientController> implements Serializable {

    private static final long serialVersionUID = 6L;
    private final ArrayList<ClientLeaderCard> leaderCards;

    public LeaderCardsMessage(ArrayList<LeaderCard> leaderCards, String username) {
        super.playerUsername = username;
        this.leaderCards = leaderCards
                .stream()
                .map(ClientLeaderCard::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void execute(ClientController target) {
        target.refreshLeaderCards(leaderCards, super.playerUsername);
    }
}
