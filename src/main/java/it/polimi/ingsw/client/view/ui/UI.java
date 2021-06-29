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
    void displayNewTurn(String player, Boolean myTurn);
    void displayChooseInitialResourcesMenu(int toChoose);
    void displayInitialLeadersMenu(ArrayList<String> cardsIDs);
    void displayWarehouse(ClientWarehouse warehouse, String username);
    void displayLeaderCardDeck(ClientLeaderCardDeck deck, String username);
    void startListening();
    void displayError(String error);
    void displayInfo(String info);
    void displayMarbleMarket(ClientMarbleMarket market);
    void displayCardMarket(ClientCardsMarket market);
    void displayStrongBox(ClientStrongbox strongbox, String username);
    void displayFaithPath(ClientFaithPath path, String username);
    void displayDevelopmentCardDecks(ClientDevelopmentCardDecks deck, String username);
    void displayOtherPlayersUsername(ArrayList<String> usernames);
    void displayHelpMessage();
    void displayTurnInfo(ArrayList<String> names, String current);
    void displayPossibleActions(boolean myTurn, boolean mainActionDone);
    void displayPickResourceMenu(MarbleSelection selection, ArrayList<Marble> selected, ArrayList<ClientLeaderCard> changeEffects, ArrayList<ClientLeaderCard> depotEffects);
    void displayBuyDevelopmentCardMenu(String id, ArrayList<ClientLeaderCard> discounts, ArrayList<ClientLeaderCard> depotEffects);
    void displayProduceMenu(ArrayList<ClientLeaderCard> effects, ArrayList<ClientLeaderCard> depotEffects);
    void startUI(ClientGame game);
    void displayUserStats(ClientPlayerBoard playerBoard);
    void showGameIsEnded();
}
