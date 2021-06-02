package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientDevelopmentCard;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.card.DevelopmentCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DevelopmentCardsMessage extends Message<ClientController> implements Serializable{

    private static final long serialVersionUID = 1L;
    private int index = 0;  // FIXME link to actual stack
    private ArrayList<ClientDevelopmentCard> developmentCards;

    public DevelopmentCardsMessage(ArrayList<DevelopmentCard> developmentCards) {
        this.developmentCards = developmentCards
                .stream()
                .map(ClientDevelopmentCard::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void execute(ClientController target) {
//        String currentPlayer = target.getGame().getCurrentPlayer();
//        developmentCards.forEach(clientDevelopmentCard -> {
//            target.getGame().getPlayerBoardMap().get(currentPlayer)
//                            .getDevelopmentCards().get(index).add(clientDevelopmentCard);
//        });
    }
}
