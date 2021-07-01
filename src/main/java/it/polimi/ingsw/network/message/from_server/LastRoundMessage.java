package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serializable;

public class LastRoundMessage extends Message<ClientController> implements Serializable {

    private static final long serialVersionUID = 48L;

    /**
     * Display message to inform client that is last round
     * @param target
     */
    public void execute(ClientController target) {
        target.viewInfoMessage("This is last round!");
    }
}
