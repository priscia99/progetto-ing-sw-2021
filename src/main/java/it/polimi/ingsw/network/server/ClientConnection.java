package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.MessageType;

public interface ClientConnection {

    void closeConnection();

    void asyncSend(Object message);
}
