package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class SetupMessage extends Message<ClientController> implements Serializable {

    private final ArrayList<String> usernames;
    private final ArrayList<Integer> resourcesToChoose;

    public SetupMessage(ArrayList<String> usernames, ArrayList<Integer> resourcesToChoose) {
        this.usernames = usernames;
        this.resourcesToChoose = resourcesToChoose;
    }

    @Override
    public void execute(ClientController target) {
        // launch setup menu
    }
}
