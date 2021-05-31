package it.polimi.ingsw.network.message.from_client;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.LeaderCard;

import java.io.Serializable;
import java.util.ArrayList;

public class ChosenInitialLeadersMessage implements Message<ServerController>, Serializable {

    private static final long serialVersionUID = 1L;
    private final ArrayList<String> leadersChosen;
    private final String playerUsername;

    public ChosenInitialLeadersMessage(ArrayList<String> leadersId, String username) {
        this.leadersChosen = leadersId;
        this.playerUsername = username;
    }

    public void execute(ServerController target) {
        target.chooseInitialLeaders(leadersChosen, playerUsername);
    }

}
