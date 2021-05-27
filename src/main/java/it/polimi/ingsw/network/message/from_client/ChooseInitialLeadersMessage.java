package it.polimi.ingsw.network.message.from_client;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.LeaderCard;

import java.io.Serializable;
import java.util.ArrayList;

public class ChooseInitialLeadersMessage implements Message<ServerController>, Serializable {

    private static final long serialVersionUID = 1L;
    private final ArrayList<LeaderCard> leadersChosen;
    private final String playerUsername;

    public ChooseInitialLeadersMessage(ArrayList<LeaderCard> leaders, String username) {
        this.leadersChosen = leaders;
        this.playerUsername = username;
    }

    public void execute(ServerController controller) {
        controller.chooseInitialLeaders(leadersChosen, playerUsername);
    }

}
