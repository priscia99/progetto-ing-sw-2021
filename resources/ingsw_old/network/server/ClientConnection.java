package it.polimi.ingsw_old.network.server;

public interface ClientConnection {

    void closeConnection();

    void asyncSend(Object message);
}
