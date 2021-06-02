package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.card.LeaderCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ChooseInitialLeadersMessage implements Message<ClientController>, Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<ClientLeaderCard> leaderCards;
    String playerUsername;

    public ChooseInitialLeadersMessage(ArrayList<LeaderCard> cards, String playerUsername){
        this.leaderCards = cards.stream().map(ClientLeaderCard::new)
                .collect(Collectors.toCollection(ArrayList::new));
        this.playerUsername = playerUsername;
    }

    @Override
    public void execute(ClientController target) {
        target.chooseInitialLeaders(playerUsername);
    }
}