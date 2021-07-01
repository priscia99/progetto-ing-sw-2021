package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serializable;

public class LorenzoWinsMessage extends Message<ClientController> implements Serializable {

    private static final long serialVersionUID = 42L;


    public LorenzoWinsMessage(){}

    /**
     * Inform client that Lorenzo has won the game
     * @param target
     */
    @Override
    public void execute(ClientController target) {
        target.setMagnificoAsWinner();
    }
}