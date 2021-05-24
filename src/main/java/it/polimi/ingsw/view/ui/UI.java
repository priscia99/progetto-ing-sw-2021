package it.polimi.ingsw.view.ui;

import it.polimi.ingsw.network.auth_data.AuthData;

public interface UI {
    public AuthData requestAuth();
    public void displayAuthFail(String errType);
    public void displayLobbyCreated(String lobbyId);
    public void displayLobbyJoined(String lobbyId);
}
