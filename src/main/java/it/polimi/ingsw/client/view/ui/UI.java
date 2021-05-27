package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.network.auth_data.*;

public interface UI {
    public AuthData requestAuth();
    public void displayAuthFail(String errType);
    public void displayLobbyCreated(String lobbyId);
    public void displayLobbyJoined(String lobbyId);
}
