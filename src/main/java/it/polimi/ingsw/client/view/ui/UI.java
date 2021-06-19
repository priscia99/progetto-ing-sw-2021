package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.*;
import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.network.auth_data.*;
import it.polimi.ingsw.server.model.card.effect.ChangeEffect;
import it.polimi.ingsw.server.model.card.effect.DepotEffect;
import it.polimi.ingsw.server.model.card.effect.DiscountEffect;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

public interface UI {

    AuthData requestAuth();
    void setController(ClientController controller);
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
    void startListening();
    void displayError(String error);
    void displayInfo(String info);
    void displayMarbleMarket(ClientMarbleMarket market);
    void displayCardMarket(ClientCardsMarket market);
    void displayStrongBox(ClientStrongbox strongbox);
    void displayFaithPath(ClientFaithPath path);
    void displayDevelopmentCardDecks(ClientDevelopmentCardDecks deck);
    void displayOtherPlayersUsername(ArrayList<String> usernames);
    void displayHelpMessage();
    void displayTurnInfo(ArrayList<String> names, String current);
    void displayPossibleActions(boolean myTurn, boolean mainActionDone);
    void displayPickResourceMenu(MarbleSelection selection, ArrayList<Marble> selected, ArrayList<ChangeEffect> changeEffects, ArrayList<DepotEffect> depotEffects);
    void displayBuyDevelopmentCardMenu(String id, ArrayList<DiscountEffect> discounts, ArrayList<DepotEffect> depotEffects);
    void displayProduceMenu(ArrayList<ProductionEffect> effects, ArrayList<DepotEffect> depotEffects);
}
