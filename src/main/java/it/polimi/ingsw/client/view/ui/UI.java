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

    /**
     * Asks to the user for authentications credentials
     * @return
     */
    AuthData requestAuth();

    /**
     * Sets the client controller
     * @param controller client controller
     */
    void setController(ClientController controller);

    /**
     * Displays the failure of authentication with an error type
     * @param errType type of authentication error
     */
    void displayAuthFail(String errType);

    /**
     * Displays the success of the creation of a lobby
     * @param lobbyId successfully created lobby id
     */
    void displayLobbyCreated(String lobbyId);

    /**
     * Displays the success of the join lobby procedure
     * @param lobbyId successfully joined lobby id
     */
    void displayLobbyJoined(String lobbyId);

    /**
     * Displays that a new turn has started
     * @param player username of the player in turn
     * @param myTurn set true if
     */
    void displayNewTurn(String player, Boolean myTurn);

    /**
     * Displays to the UI that the player has to choose initial resources
     * @param toChoose number of resources to choose
     */
    void displayChooseInitialResourcesMenu(int toChoose);

    /**
     * Displays to the UI that the player has to choose initial leader cards
     * @param cardsIDs
     */
    void displayInitialLeadersMenu(ArrayList<String> cardsIDs);

    /**
     * Displays to the UI a specific warehouse
     * @param warehouse warehouse to display
     * @param username name of the warehouse's owner
     */
    void displayWarehouse(ClientWarehouse warehouse, String username);

    /**
     * Displays to the UI a specific leader cards deck
     * @param deck leader card deck to display
     * @param username name of the deck's owner
     */
    void displayLeaderCardDeck(ClientLeaderCardDeck deck, String username);

    /**
     * Enable the CLI to listen for players commands
     */
    void startListening();

    /**
     * Displays to the UI a generic error
     * @param error error to display
     */
    void displayError(String error);

    /**
     * Displays to the UI a generic info
     * @param info info to display
     */
    void displayInfo(String info);

    /**
     * Displays to the UI the marble market
     * @param market marble market to display
     */
    void displayMarbleMarket(ClientMarbleMarket market);

    /**
     * Displays to the UI the development cards market
     * @param market development cards market to display
     */
    void displayCardMarket(ClientCardsMarket market);

    /**
     * Display to the UI a specific strongbox
     * @param strongbox strongbox to display
     * @param username name of the strongbox's owner
     */
    void displayStrongBox(ClientStrongbox strongbox, String username);

    /**
     * Display to the UI a specific faith path
     * @param path faith path to display
     * @param username name of the faith path's owner
     */
    void displayFaithPath(ClientFaithPath path, String username);

    /**
     * Display to the UI specific development cards decks
     * @param deck player development cards deck to display
     * @param username name of the decks' owner
     */
    void displayDevelopmentCardDecks(ClientDevelopmentCardDecks deck, String username);

    /**
     * Display to the UI the name of players
     * @param usernames list of usernames
     */
    void displayOtherPlayersUsername(ArrayList<String> usernames);

    /**
     * Display an command help message in GUI
     */
    void displayHelpMessage();

    /**
     * Display turn information in CLI
     * @param names list of usernames
     * @param current username of the player in turn
     */
    void displayTurnInfo(ArrayList<String> names, String current);

    /**
     * Displays all possible actions in GUI
     * @param myTurn set true if it's the player's turn
     * @param mainActionDone set true if the main action has already done in this turn
     */
    void displayPossibleActions(boolean myTurn, boolean mainActionDone);

    /**
     * Displays pick resources screen in UI
     * @param selection selected marble market row / column orientation
     * @param selected selected marbles from the market
     * @param changeEffects list of optional conversions for white marbles
     * @param depotEffects list of activated leader cards with depot effects
     */
    void displayPickResourceMenu(MarbleSelection selection, ArrayList<Marble> selected, ArrayList<ClientLeaderCard> changeEffects, ArrayList<ClientLeaderCard> depotEffects);

    /**
     * Displays buy development card screen in UI
     * @param id card id that player wants to buy
     * @param discounts list of activated leader cards that have a discount effect
     * @param depotEffects list of activated leader cards with depot effects
     */
    void displayBuyDevelopmentCardMenu(String id, ArrayList<ClientLeaderCard> discounts, ArrayList<ClientLeaderCard> depotEffects);

    /**
     * Displays production screens in UI
     * @param effects list of activated leader cards that have a production effect
     * @param depotEffects list of activated leader cards that have a depot effect
     */
    void displayProduceMenu(ArrayList<ClientLeaderCard> effects, ArrayList<ClientLeaderCard> depotEffects);

    /**
     * Initializes the UI with information provided by client game
     * @param game the client game
     */
    void startUI(ClientGame game);

    /**
     * Displays user stats for the selected playerboard
     * @param playerBoard user's playerboard
     */
    void displayUserStats(ClientPlayerBoard playerBoard);

    /**
     * Displays to the UI that the game is ended
     */
    void showGameIsEnded();

}
