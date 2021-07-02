package it.polimi.ingsw.network.auth_data;

import java.io.Serializable;

public class AuthData implements Serializable {

    private static final long serialVersionUID = 1050L;
    String username;    // username chosen by client
    String lobby;       // lobby code given by client
    boolean createNewLobby;
    int lobbyPlayerNumber;

    /**
     *
     * Create new auth data object, when joining an existing lobby
     * @param playerUsername
     * @param lobbyToJoin
     */
    private AuthData(String playerUsername, String lobbyToJoin){
        this.username = playerUsername;
        this.lobby = lobbyToJoin;
        createNewLobby = false;
        lobbyPlayerNumber = 0;
    }

    /**
     * Create a new auth data object when creating a new lobby
     * @param playerUsername
     * @param lobbyPlayerNumber
     */
    private AuthData(String playerUsername, int lobbyPlayerNumber){
        this.username = playerUsername;
        this.lobbyPlayerNumber = lobbyPlayerNumber;
        createNewLobby = true;
    }

    /**
     * Factory method used when trying to join lobby
     * @param playerUsername
     * @param lobbyToJoin
     * @return
     */
    public static AuthData joinLobby(String playerUsername, String lobbyToJoin){
        return new AuthData(playerUsername, lobbyToJoin);
    }

    /**
     * Factory method used when trying creating lobby
     * @param playerUsername
     * @param lobbyPlayerNumber
     * @return
     */
    public static AuthData createLobby(String playerUsername, int lobbyPlayerNumber){
        return new AuthData(playerUsername, lobbyPlayerNumber);
    }

    public String getUsername(){
        return username;
    }

    public String getLobby() {
        return lobby;
    }

    public boolean isCreateNewLobby() {
        return createNewLobby;
    }

    public int getLobbyPlayerNumber() {
        return lobbyPlayerNumber;
    }
}
