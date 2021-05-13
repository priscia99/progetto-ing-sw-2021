package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.observer.Observer;

public interface ClientConnection {

    void closeConnection();

    void addObserver(Observer<String> observer);

    void asyncSend(Object message);
}
