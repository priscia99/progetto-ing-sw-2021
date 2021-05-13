package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.observer.Observer;

public interface ClientConnection {

    void closeConnection();

    void addObserver(Observer<Message> observer);

    void asyncSend(Object message);
}
