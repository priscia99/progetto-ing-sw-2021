package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import it.polimi.ingsw.network.auth_data.*;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

public interface UI {

    AuthData requestAuth();
    void displayAuthFail(String errType);
    void displayLobbyCreated(String lobbyId);
    void displayLobbyJoined(String lobbyId);
    void displayGameStarted();
    void displayNewTurn(String player, Boolean myTurn);
    void displayLeaderCard(ClientLeaderCard clientLeaderCard);
    void displayLeaderCardDeck(ClientLeaderCardDeck clientLeaderCardDeck);
    HashMap<ResourcePosition, ResourceType> chooseInitialResources(int toChoose);
    ArrayList<String> chooseInitialLeaders();
}