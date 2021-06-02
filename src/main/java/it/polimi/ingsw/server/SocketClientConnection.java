package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.FullLobbyException;
import it.polimi.ingsw.exceptions.InvalidLobbyException;
import it.polimi.ingsw.network.auth_data.AuthData;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.service_message.ServiceMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.controller.ServerController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientConnection extends Observable<Message<ServerController>> implements ClientConnection, Runnable {
    private Lobby lobby;
    private final Socket socket;  // single socket connection with one client
    private ObjectOutputStream out; // output stream to the client
    private final Server server;
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
            System.out.println("Sent message to " + this);
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {

        String username = null;    // username of the player (client)
        String lobbyId = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            this.asyncSend("auth_request");
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            boolean success = true;
            do{
                if(!success){
                    this.asyncSend("auth_request");
                    success = true;
                }
                AuthData authData = (AuthData) in.readObject();
                System.out.println(authData.getUsername());
                try {
                    if (authData.isCreateNewLobby()) {
                        lobby = server.lobby(authData.getLobbyPlayerNumber(), authData.getUsername(), this);
                        // try to create a new lobby
                        this.asyncSend("auth_created#" + lobby.getLobbyId());
                    } else {
                        System.out.println("User attenting to join lobby");
                        lobby = server.lobby(authData.getLobby(), authData.getUsername(), this);
                        this.asyncSend("auth_joined#" + lobby.getLobbyId());
                    }
                }catch (InvalidLobbyException e){
                    this.asyncSend("auth_error#invalid_lobby");
                    success = false;
                }
                catch (FullLobbyException e){
                    this.asyncSend("auth_error#full_lobby");
                    success = false;
                }
            }while(!success);

            // while the connection is alive read every message and notify it to the server
            while(isAlive()) {
                Object inputObject = in.readObject();
                if(inputObject instanceof ServiceMessage){
                    ServiceMessage<Lobby> message = (ServiceMessage) inputObject;
                    message.execute(lobby);
                }
                // notify(inputObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally { // when the connection die close it
            close();
        }

    }
}
