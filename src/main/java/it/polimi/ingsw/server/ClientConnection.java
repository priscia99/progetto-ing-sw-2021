package it.polimi.ingsw.server;

/**
 * Interface that models the common actions of the connection with the client.
 */
public interface ClientConnection {

    /**
     * Terminate and close the connection.
     */
    void closeConnection();

    /**
     * Send asynchronously messages to the client.
     * @param message the object to send
     */
    void asyncSend(Object message);
}
