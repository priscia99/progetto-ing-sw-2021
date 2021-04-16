package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements ClientConnection, Runnable {

    private Socket socket;  // single socket connection with one client
    private ObjectOutputStream out; // output stream to the client
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
        Scanner in;         // input stream valid locally
        String username;    // username of the player (client)
        String lobbyId;
        try {
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            // FIXME: call view (temporary)
            this.send("Welcome!\n");  // read username and lobby id from the client
            this.send("Enter a username: ");
            String inBuffer = in.nextLine();
            username = inBuffer;
            this.send("\nDo you want to create a new lobby? [Y/N] \n");
            inBuffer = in.nextLine();
            if (inBuffer.equalsIgnoreCase("y")) {
                this.send("\nSpecify the dimension of the lobby: [4 max] \n");
                inBuffer = in.nextLine();
                int dimension = Integer.parseInt(inBuffer);
                server.lobby(dimension, username, this);   // add this client connection to the server
            } else if (inBuffer.equalsIgnoreCase("n")) {
                this.send("\nDo you want to join a lobby? [Y/N] \n");
                inBuffer = in.nextLine();
                lobbyId = inBuffer;
                server.lobby(lobbyId, username, this);   // add this client connection to the server
            } else {
                throw new IllegalArgumentException("Wrong response!!!");
            }

            // while the connection is alive read every message and notify it to the server
            while(isAlive()) {
                inBuffer = in.nextLine();
                notify(inBuffer);
            }
        } catch (IOException e) {
            System.err.println("Error!" + e.getMessage());
        } finally { // when the connection die close it
            close();
        }
    }
}
