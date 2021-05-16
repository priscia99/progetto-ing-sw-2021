package it.polimi.ingsw.network.server;

public interface ClientConnection {

    void closeConnection();

    void asyncSend(Object message);
}
