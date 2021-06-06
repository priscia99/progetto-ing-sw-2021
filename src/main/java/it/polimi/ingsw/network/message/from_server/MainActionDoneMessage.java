package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serializable;

public class MainActionDoneMessage extends Message<ClientController> implements Serializable{

    private static final long serialVersionUID = 1L;
    private final boolean done;

    public MainActionDoneMessage(boolean done){
        this.done = done;
    }

    @Override
    public void execute(ClientController target) {
        target.setMainActionDone(this.done);
    }
}
