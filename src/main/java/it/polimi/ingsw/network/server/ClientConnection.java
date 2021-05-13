package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.observer.ObserverWithOption;

public interface ClientConnection {

    void closeConnection();

    // FIXME please! End my sufferance
    void addObserver(ObserverWithOption<Message> observer);

    void asyncSend(Object message);
}
