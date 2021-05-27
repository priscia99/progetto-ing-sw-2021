package it.polimi.ingsw_old.network.auth_data;

import java.io.Serializable;

public class AuthData implements Serializable {
    private static final long serialVersionUID = 1;
    String username;
    String lobby;
    boolean createNewLobby;
    int lobbyPlayerNumber;

    public AuthData(String playerUsername, String lobbyToJoin){
        this.username = playerUsername;
        this.lobby = lobbyToJoin;
        createNewLobby = false;
        lobbyPlayerNumber = 0;
    }

    public AuthData(String playerUsername, int lobbyPlayerNumber){
        this.username = playerUsername;
        this.lobbyPlayerNumber = lobbyPlayerNumber;
        createNewLobby = true;
    }

    public static AuthData joinLobby(String playerUsername, String lobbyToJoin){
        return new AuthData(playerUsername, lobbyToJoin);
    }

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
