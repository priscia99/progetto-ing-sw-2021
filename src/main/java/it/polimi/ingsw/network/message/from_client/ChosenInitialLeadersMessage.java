package it.polimi.ingsw.network.message.from_client;


import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.LeaderCard;

import java.io.Serializable;
import java.util.ArrayList;

public class ChosenInitialLeadersMessage extends Message<ServerController> implements Serializable {

    private static final long serialVersionUID = 26L;
    private final ArrayList<String> leadersChosen;
    private final String playerUsername;

    public ChosenInitialLeadersMessage(ArrayList<String> leadersId, String username) {
        this.leadersChosen = leadersId;
        this.playerUsername = username;
    }

    public void execute(ServerController target) throws Exception {
        target.chooseInitialLeaders(leadersChosen, playerUsername);
    }

}
