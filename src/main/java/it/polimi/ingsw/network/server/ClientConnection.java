package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.observer.ObserverWithOption;

public interface ClientConnection {

    void closeConnection();

    void asyncSend(Object message);
}
