package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import it.polimi.ingsw.client.model.ClientWarehouse;
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
    HashMap<ResourcePosition, ResourceType> chooseInitialResources(int toChoose);
    ArrayList<String> chooseInitialLeaders(ArrayList<String> cardsIDs);
    void displayWarehouse(ClientWarehouse warehouse);
    void displayLeaderCardDeck(ClientLeaderCardDeck deck);
    void startListening(ClientController controller);
    void displayError(String error);
}
