package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.FullLobbyException;
import it.polimi.ingsw.exceptions.InvalidLobbyException;
import it.polimi.ingsw.network.auth_data.AuthData;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.service_message.ServiceMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.utils.CustomLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientConnection extends Observable<Message<ServerController>> implements ClientConnection, Runnable {
    private Lobby lobby;
    private final Socket socket;  // single socket connection with one client
    private ObjectOutputStream out; // output stream to the client
    private final Server server;
    private AuthData authData;
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
        try {
            socket.close();
        } catch (IOException e) {
            CustomLogger.getLogger().info("Error when closing socket!");
        }
        alive = false;
    }

    private void close() {
        CustomLogger.getLogger().info("["+lobby.getLobbyId()+"] "+ "Clearing connection with " + authData.getUsername());
        server.unregisterConnection(this);
        CustomLogger.getLogger().info("["+lobby.getLobbyId()+"] "+ "Connection successfully removed with " + authData.getUsername());
        this.closeConnection();
    }

    /**
     * Create a new thread to send a message asynchronously.
     * @param message object to send
     */
    @Override
    public void asyncSend(Object message) {
        new Thread(() -> send(message)).start();
    }

    private synchronized void send(Object message){
        try {
            String source = (lobby == null) ? "server" : lobby.getLobbyId();
            String target = (authData == null) ? this.toString() : authData.getUsername();
            CustomLogger.getLogger().info("["+source+"] "+ " sent " + message.getClass() + " to " + target);
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            CustomLogger.getLogger().severe(e.getMessage());
        }
    }

    @Override
    public void run() {
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
                authData = (AuthData) in.readObject();
                CustomLogger.getLogger().info("New player connected:" + authData.getUsername());
                try {
                    if (authData.isCreateNewLobby()) {
                        lobby = server.lobby(authData.getLobbyPlayerNumber(), authData.getUsername(), this);
                        this.asyncSend("auth_created#" + lobby.getLobbyId());
                        CustomLogger.getLogger().info(authData.getUsername() + " created new lobby " + lobby.getLobbyId());
                    } else {
                        lobby = server.lobby(authData.getLobby(), authData.getUsername(), this);
                        this.asyncSend("auth_joined#" + lobby.getLobbyId());
                        CustomLogger.getLogger().info(authData.getUsername() + " joined lobby " + lobby.getLobbyId());
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

            while(isAlive()) {
                Object inputObject = in.readObject();
                if(inputObject instanceof ServiceMessage){
                    ServiceMessage<Lobby> message = (ServiceMessage) inputObject;
                    message.execute(lobby);
                }
                else if(inputObject instanceof Message){
                    CustomLogger.getLogger().info("["+lobby.getLobbyId()+"] "+ authData.getUsername() + " sent a " + inputObject.getClass());
                    notify((Message<ServerController>) inputObject);
                }
            }
        } catch (Exception e) {
            assert authData != null;
            CustomLogger.getLogger().info("["+lobby.getLobbyId()+"] "+ authData.getUsername() + " disconnected." );
        } finally {
            close();
        }

    }
}
