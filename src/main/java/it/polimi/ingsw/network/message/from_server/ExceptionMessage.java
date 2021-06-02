package it.polimi.ingsw.network.message.from_server;


import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serializable;

public class ExceptionMessage extends Message<ClientController> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Exception exception;

    public ExceptionMessage(Exception exception) {
        this.exception = exception;
    }

    public void execute(ClientController target) {

    }
}
