package it.polimi.ingsw_old.view.ui;

import it.polimi.ingsw_old.network.auth_data.AuthData;

public interface UI {
    public AuthData requestAuth();
    public void displayAuthFail(String errType);
    public void displayLobbyCreated(String lobbyId);
    public void displayLobbyJoined(String lobbyId);
}
