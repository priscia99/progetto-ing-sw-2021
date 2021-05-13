package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.observer.ObserverWithOption;

public interface ClientConnection {

    void closeConnection();

    void addObserver(ObserverWithOption<Message> observer);

    void asyncSend(Object message);
}
