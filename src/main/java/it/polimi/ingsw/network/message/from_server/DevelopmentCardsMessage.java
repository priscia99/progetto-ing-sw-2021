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
    private int index;
    private ClientDevelopmentCard developmentCard;


    public DevelopmentCardsMessage(DevelopmentCard developmentCard, int index, String username) {
        super.setPlayerUsername(username);
        this.index = index;
        this.developmentCard = new ClientDevelopmentCard(developmentCard);
    }

    public void execute(ClientController target) {
         String currentPlayer = target.getGame().getCurrentPlayer();
         target.getGame().getPlayerBoardMap().get(currentPlayer)
                .getDevelopmentCards().addCard(developmentCard, index);
    }
}
