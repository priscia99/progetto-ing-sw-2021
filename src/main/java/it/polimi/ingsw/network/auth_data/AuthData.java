package it.polimi.ingsw.network.auth_data;

import java.io.Serializable;

public class AuthData implements Serializable {

    /*
        player : {
            username : String
        }
        lobby : {
            id : String
            join : Boolean
            dimension : Int
        }
     */

    /*
        AuthRequest         -- username
        JoinLobbyRequest    -- lobbyToJoin
        CreateLobbyRequest  -- lobbyPlayerNumber
     */

    private static final long serialVersionUID = 1;
    String username;    // username chosen by client
    String lobby;       // lobby code given by client
    boolean createNewLobby;
    int lobbyPlayerNumber;

    private AuthData(String playerUsername, String lobbyToJoin){
        this.username = playerUsername;
        this.lobby = lobbyToJoin;
        createNewLobby = false;
        lobbyPlayerNumber = 0;
    }

    private AuthData(String playerUsername, int lobbyPlayerNumber){
        this.username = playerUsername;
        this.lobbyPlayerNumber = lobbyPlayerNumber;
        createNewLobby = true;
    }

    // tries to join a lobby
    public static AuthData joinLobby(String playerUsername, String lobbyToJoin){
        return new AuthData(playerUsername, lobbyToJoin);
    }

    // tries to create a new lobby
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
