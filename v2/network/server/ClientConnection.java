package v2.network.server;

public interface ClientConnection {

    void closeConnection();

    void asyncSend(Object message);
}
