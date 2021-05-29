package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serializable;

public class LastRoundMessage implements Message<ClientController>, Serializable {

    private static final long serialVersionUID = 1L;

    public void execute(ClientController target) {

    }
}
