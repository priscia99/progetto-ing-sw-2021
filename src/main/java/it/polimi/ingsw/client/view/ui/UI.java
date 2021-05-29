package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.network.auth_data.*;

public interface UI {

    AuthData requestAuth();
    void displayAuthFail(String errType);
    void displayLobbyCreated(String lobbyId);
    void displayLobbyJoined(String lobbyId);
    void displayGameStarted();
    void displayNewTurn(String player, Boolean myTurn);
}
