package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serializable;

public class GameReadyMessage extends Message<ClientController> implements Serializable {

    private static final long serialVersionUID = 17L;

    public GameReadyMessage(){
    }

    @Override
    public void execute(ClientController target) {
        System.out.println("Game ready " + target);
        target.startListening();
    }
}
