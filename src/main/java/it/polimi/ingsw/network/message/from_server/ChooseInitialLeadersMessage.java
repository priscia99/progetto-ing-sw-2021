package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.card.LeaderCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ChooseInitialLeadersMessage extends Message<ClientController> implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<ClientLeaderCard> leaderCards;
    public ChooseInitialLeadersMessage(ArrayList<LeaderCard> cards, String playerUsername){
        this.leaderCards = cards.stream().map(ClientLeaderCard::new)
                .collect(Collectors.toCollection(ArrayList::new));
        super.setPlayerUsername(playerUsername);
    }

    @Override
    public void execute(ClientController target) {
        target.refreshLeaderCards(leaderCards, super.getPlayerUsername());
        target.viewChooseInitialLeaders(super.getPlayerUsername());
    }
}
