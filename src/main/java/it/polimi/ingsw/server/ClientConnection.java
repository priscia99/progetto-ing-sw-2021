package it.polimi.ingsw.server;

public interface ClientConnection {

    void closeConnection();

    void asyncSend(Object message);
}