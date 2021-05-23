package v2.network.server;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientConnection extends Observable<Action> implements ClientConnection, Runnable {

    private Socket socket;  // single socket connection with one client
    private ObjectOutputStream out; // output stream to the client
    private ObjectInputStream in;
    private Server server;
    private boolean alive = true;

    /**
     * Create a new connection with a client that is not ready to communicate yet.
     * @param socket socket connection between client and server
     * @param server server that host the connection
     */
    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isAlive() {
        return alive;
    }

    /**
     * Stop the execution of the thread, close the connection and deregister it from the server.
     */
    @Override
    public void closeConnection() {
        this.send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        alive = false;
    }

    private void close() {
        this.closeConnection();
        System.out.println("deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    /**
     * Create a new thread to send a message asynchronously.
     * @param message object to send
     */
    @Override
    public void asyncSend(Object message) {
        new Thread((Runnable) () -> {
                send(message);
            }).start();
    }

    private synchronized void send(Object message){
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        String username;    // username of the player (client)
        String lobbyId;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            // FIXME
            // tell client server is ready to listen
            this.send("Welcome!");  // read username and lobby id from the client
            // create lobby and insert this connection
            /*
            this.send("Enter a username: ");
            Message inBuffer = (Message) in.readObject();
            username = inBuffer.getPayload();
            this.send("Do you want to create a new lobby? [Y/N]");
            inBuffer = in.readObject();
            if (inBuffer.equalsIgnoreCase("y")) {
                this.send("Specify the dimension of the lobby: [4 max]");
                inBuffer = in.readObject();
                int dimension = Integer.parseInt(inBuffer);
                server.lobby(dimension, username, this);   // add this client connection to the server
            } else if (inBuffer.equalsIgnoreCase("n")) {
                this.send("Enter a lobby id: ");
                inBuffer = in.readObject();
                lobbyId = inBuffer;
                server.lobby(lobbyId, username, this);   // add this client connection to the server
            } else {
                throw new IllegalArgumentException("Wrong response!!!");
            }
            */
            // while the connection is alive read every message and notify it to the server
            while(isAlive()) {
                Action incomingAction = (Action) in.readObject();
                notify(incomingAction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally { // when the connection die close it
            close();
        }
    }
}
