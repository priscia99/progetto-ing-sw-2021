package it.polimi.ingsw.network.message.from_server;


import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serial;
import java.io.Serializable;

public class ExceptionMessage extends Message<ClientController> implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;
    private final String errorMessage;
    private final String player;

    public ExceptionMessage(String exception, String player) {
        this.player = player;
        this.errorMessage = exception;
    }

    /**
     * Display error message on client
     * @param target
     */
    public void execute(ClientController target) {
        if(target.getGame().getMyUsername().equals(player)) target.viewErrorMessage(errorMessage);
    }
}
