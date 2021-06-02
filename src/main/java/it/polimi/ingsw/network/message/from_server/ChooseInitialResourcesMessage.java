package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.card.LeaderCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ChooseInitialResourcesMessage implements Message<ClientController>, Serializable {

    private static final long serialVersionUID = 1L;

    private int toChoose;
    String playerUsername;

    public ChooseInitialResourcesMessage(String playerUsername, int count){
        this.toChoose = count;
        this.playerUsername = playerUsername;
    }

    @Override
    public void execute(ClientController target) {
        target.chooseInitialResources(toChoose, playerUsername);
    }
}