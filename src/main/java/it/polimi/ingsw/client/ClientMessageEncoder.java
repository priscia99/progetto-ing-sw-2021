package it.polimi.ingsw.client;

import it.polimi.ingsw.observer.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.server.controller.ServerController;

public class ClientMessageEncoder implements Observer<Message<ServerController>> {
    Client client;
    public ClientMessageEncoder(Client client) {
        this.client = client;
    }

    @Override
    public void update(Message object) {
        client.sendToSocket(object);
    }
}
