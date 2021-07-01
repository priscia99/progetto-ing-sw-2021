package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.network.auth_data.AuthData;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.utils.CustomLogger;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GUI implements UI{
    private ClientController controller;    // reference to client controller
    private Stage primaryStage;             // GUI Application primary stage
    private String selectedPlayerBoard = null;
    private String myUsername = null;
    private boolean arePlayerStatsInitialized = false;

    /**
     * Initialize the GUI by setting the primary stage
     * @param primaryStage GUI primary stage
     */
    public GUI(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    /**
     * Loads the auth screen in GUI
     * @param client client object
     */
    public void loadAuthScreen(Client client){
        Platform.runLater(() -> SceneController.requestAuth(primaryStage, client));
    }

    /**
     * Empty method for GUI
     * @return
     */
    @Override
    public AuthData requestAuth() {return null;}

    @Override
    public void setController(ClientController controller) {
        this.controller = controller;
    }

    /**
     * Display error from unsuccessful authentication
     * @param errType type of authentication error
     */
    @Override
    public void displayAuthFail(String errType) {
        String errorMessage;
        if(errType.equals("full_lobby"))
            errorMessage = "Cannot join the requested lobby because it's full.";
        else if(errType.equals("invalid_lobby"))
            errorMessage = "The requested lobby is not valid.";
        else
            errorMessage = "Generic error.";
        Platform.runLater(() -> ((TextField)primaryStage.getScene().lookup("#login-message")).setText(errorMessage));
    }

    /**
     * Display message when lobby creation is successful
     * @param lobbyId successfully created lobby id
     */
    @Override
    public void displayLobbyCreated(String lobbyId) {
        primaryStage.getScene().lookup("#join-button").setDisable(true);
        primaryStage.getScene().lookup("#create-button").setDisable(true);
        Platform.runLater(() -> ((TextField)primaryStage.getScene().lookup("#login-message")).setText("Created lobby " + lobbyId));
    }

    /**
     * Display message when lobby joining is successful
     * @param lobbyId successfully joined lobby id
     */
    @Override
    public void displayLobbyJoined(String lobbyId) {
        primaryStage.getScene().lookup("#join-button").setDisable(true);
        primaryStage.getScene().lookup("#create-button").setDisable(true);
        if(primaryStage.getScene().lookup("#login-message") != null){
            Platform.runLater(() -> ((TextField)primaryStage.getScene().lookup("#login-message")).setText("Joined lobby " + lobbyId));
        }
    }

    /**
     * Set the GUI state to the one needed when starting a player turn
     * @param player username of the player in turn
     * @param myTurn set true if player is current player turn
     */
    @Override
    public void displayNewTurn(String player, Boolean myTurn) {
        Platform.runLater(() -> {
            SceneController.getMainGUIController().getStatsController().refreshTurn(player);
        });
        if(myTurn){
            Platform.runLater(() -> {
                SceneController.getMainGUIController().startingTurn();
                SceneController.getMainGUIController().enableAllActions();
                SceneController.displayPopupMessage(primaryStage, player + ", it's your turn!");
            });
        }else{
            Platform.runLater(() -> {
                SceneController.getMainGUIController().disableButtons();
                SceneController.getMainGUIController().disableAllActions();
            });
        }
    }

    /**
     * Display panel to choose initial resources
     * @param toChoose number of resources to choose
     */
    @Override
    public void displayChooseInitialResourcesMenu(int toChoose) {
        Platform.runLater(() -> {
            SceneController.getMainGUIController().getChooseResourcesController().activeScreen(toChoose);
        });
    }

    /**
     * Display panel to choose initial leaders
     * @param cardsIDs IDs of card to choose from
     */
    @Override
    public void displayInitialLeadersMenu(ArrayList<String> cardsIDs) {
        Platform.runLater(() -> {
            SceneController.getMainGUIController().getChooseLeadersController().activeScreen(cardsIDs);
        });
    }


    /**
     * Refresh warehouse content
     * @param warehouse warehouse to display
     * @param username name of the warehouse's owner
     */
    @Override
    public void displayWarehouse(ClientWarehouse warehouse, String username) {
        if(isToRefresh(username)) {
            Platform.runLater(() -> SceneController.getMainGUIController().getWarehouseController().refreshWarehouse(warehouse, isMine(username)));
        }
    }

    /**
     * Refreshes Leader cards deck view in GUI
     * @param deck Player leader cards
     */
    @Override
    public void displayLeaderCardDeck(ClientLeaderCardDeck deck, String username) {
        if (isToRefresh(username)) {
            Platform.runLater(() -> {
                SceneController.getMainGUIController().getLeaderCardsController().refreshLeaderCards(deck, isMine(username));
            }
            );
        }
    }

    /**
     * Empty method for GUI
     */
    @Override
    public void startListening() {
    }

    /**
     * Display popup showing error message
     * @param error error to display
     */
    @Override
    public void displayError(String error) {
        CustomLogger.getLogger().severe(error);
        Platform.runLater(() -> SceneController.displayPopupError(primaryStage, error));
    }

    /**
     * Display popup showing info message
     * @param info info to display
     */
    @Override
    public void displayInfo(String info) {
        Platform.runLater(() -> SceneController.displayPopupMessage(primaryStage, info));
    }

    /**
     * Refresh marble marlet content
     * @param market marble market to display
     */
    @Override
    public void displayMarbleMarket(ClientMarbleMarket market) {
        Platform.runLater(() -> SceneController.getMainGUIController().getMarbleMarketController().setMarbleMarket(market));
    }

    /**
     * Refresh card market content
     * @param market development cards market to display
     */
    @Override
    public void displayCardMarket(ClientCardsMarket market) {
        Platform.runLater(() -> SceneController.getMainGUIController().getDevelopmentCardMarketController().setCardsMarket(market));
    }

    /**
     * Refresh strongbox content
     * @param strongbox strongbox to display
     * @param username name of the strongbox's owner
     */
    @Override
    public void displayStrongBox(ClientStrongbox strongbox, String username) {
        if (isToRefresh(username)) {
            Platform.runLater(() -> SceneController.getMainGUIController().getStrongBoxController().refreshStrongbox(strongbox, isMine(username)));
        }
    }

    /**
     * Refresh faith path content
     * @param path faith path to display
     * @param username name of the faith path's owner
     */
    @Override
    public void displayFaithPath(ClientFaithPath path, String username) {
        if (isToRefresh(username)) {
            Platform.runLater(() -> {
                SceneController.getMainGUIController().getFaithPathController().refreshFaithPath(path);
                SceneController.getMainGUIController().getStatsController().refreshFaithPoints(path.getFaithPoints(), username);
            });
        }
    }

    /**
     * Refresh development cards content
     * @param deck player development cards deck to display
     * @param username name of the decks' owner
     */
    @Override
    public void displayDevelopmentCardDecks(ClientDevelopmentCardDecks deck, String username) {
        if (isToRefresh(username)) {
            Platform.runLater(() -> {
                SceneController.getMainGUIController().getDevelopmentCardsController().refreshDevelopmentCards(deck, isMine(username));
                SceneController.getMainGUIController().getStatsController().refreshDevelopmentCardNumbers(deck.getCardsNumber(), username);
            });
        }
    }

    /**
     * Empty method in GUI
     * @param usernames list of usernames
     */
    @Override
    public void displayOtherPlayersUsername(ArrayList<String> usernames) {

    }

    /**
     * Empty method in GUI
     */
    @Override
    public void displayHelpMessage() {

    }

    /**
     * Empty method in GUI
     * @param names list of usernames
     * @param current username of the player in turn
     */
    @Override
    public void displayTurnInfo(ArrayList<String> names, String current) {

    }

    /**
     * Empty method in GUI
     * @param myTurn set true if it's the player's turn
     * @param mainActionDone set true if the main action has already done in this turn
     */
    @Override
    public void displayPossibleActions(boolean myTurn, boolean mainActionDone) {

    }

    /**
     * Set the GUI state to the one needed to select resources from positions
     * @param selection selected marble market row / column orientation
     * @param selected selected marbles from the market
     * @param changeEffects list of optional conversions for white marbles
     * @param depotEffects list of activated leader cards with depot effects
     */
    @Override
    public void displayPickResourceMenu(MarbleSelection selection, ArrayList<Marble> selected, ArrayList<ClientLeaderCard> changeEffects, ArrayList<ClientLeaderCard> depotEffects) {
        SceneController.getMainGUIController().getWarehouseController().insertResourcesToDepot(selection, selected, changeEffects, depotEffects);
    }

    /**
     * Empty for GUI
     * @param id card id that player wants to buy
     * @param discounts list of activated leader cards that have a discount effect
     * @param depotEffects list of activated leader cards with depot effects
     */
    @Override
    public void displayBuyDevelopmentCardMenu(String id, ArrayList<ClientLeaderCard> discounts, ArrayList<ClientLeaderCard> depotEffects) {

    }

    /**
     * Display panel to choose productions to activate
     * @param effects list of activated leader cards that have a production effect
     * @param depotEffects list of activated leader cards that have a depot effect
     */
    @Override
    public void displayProduceMenu(ArrayList<ClientLeaderCard> effects, ArrayList<ClientLeaderCard> depotEffects) {
        Platform.runLater(() -> {
            SceneController.getMainGUIController().getProductionController().openProductionSelection(effects);
        });
    }

    /**
     * Initialize GUI with current game data
     * @param game the client game
     */
    @Override
    public void startUI(ClientGame game) {
        this.selectedPlayerBoard = game.getMyUsername();
        this.myUsername = game.getMyUsername();
        Platform.runLater(() -> SceneController.showGameScene(primaryStage));
        Platform.runLater(() -> {
            displayUserStats(game.getPlayerBoardMap().get(myUsername));
            SceneController.getMainGUIController().initGUI(controller);
            SceneController.getMainGUIController().getFaithPathController().refreshFaithPath(game.getPlayerBoardMap().get(myUsername).getFaithPath());
            SceneController.getMainGUIController().getWarehouseController().refreshWarehouse(game.getPlayerBoardMap().get(game.getMyUsername()).getWarehouse(), true);
            SceneController.getMainGUIController().getStrongBoxController().refreshStrongbox(game.getPlayerBoardMap().get(myUsername).getStrongbox(), true);
            SceneController.getMainGUIController().getLeaderCardsController().refreshLeaderCards(game.getPlayerBoardMap().get(game.getMyUsername()).getClientLeaderCards(), true);
            SceneController.getMainGUIController().getPlayerBoardController().initClientSelector(new ArrayList<>(game.getPlayerBoardMap().keySet()));
            SceneController.getMainGUIController().getPlayerBoardController().setUsername(game.getMyUsername(), true);
            if(game.getClientMarbleMarket().getNotForSale()!=null){
                SceneController.getMainGUIController().getMarbleMarketController().setMarbleMarket(game.getClientMarbleMarket());
            }
            if(!game.getClientCardsMarket().getDecks().isEmpty()){
                SceneController.getMainGUIController().getDevelopmentCardMarketController().setCardsMarket(game.getClientCardsMarket());
            }
        });
    }

    /**
     * Refresh user stats section
     * @param playerBoard user's playerboard
     */
    @Override
    public void displayUserStats(ClientPlayerBoard playerBoard) {
        if(!arePlayerStatsInitialized){
            controller.getGame().getPlayerBoardMap().entrySet().forEach(
                    entry -> {
                        Platform.runLater(() ->  SceneController.getMainGUIController().getStatsController().initStats(entry.getValue()));
                    }
            );
            arePlayerStatsInitialized = true;
        }
        else {
            Platform.runLater(() -> {
                SceneController.getMainGUIController().getStatsController().initStats(playerBoard);
            });
        }
    }

    /**
     * Display alert when game is ended
     */
    @Override
    public void showGameIsEnded() {
        Platform.runLater( () -> SceneController.displayEndGame());
    }

    /**
     * Sets the playerboard username in GUI
     * @param username name of the player whose playerboard has to be shown
     * @param isMine set true if the player is the same as the one who is connected with this client instance
     */
    public void setPlayerBoardUsername(String username, boolean isMine){
        this.selectedPlayerBoard = username;
        if(isMine && username.equals(controller.getGame().getCurrentPlayer())){
            Platform.runLater(() -> {
                SceneController.getMainGUIController().startingTurn();
            });
        }else{
            Platform.runLater(() -> {
                SceneController.getMainGUIController().disableButtons();
            });
        }
        Platform.runLater(() -> SceneController.getMainGUIController().getPlayerBoardController().setUsername(username, isMine));
    }

    /**
     *
     * @param username name of the player
     * @return true if the playerboard needs to be refreshed, false otherwise
     */
    private boolean isToRefresh(String username){
        return username.equals(selectedPlayerBoard);
    }

    /**
     *
     * @param username name of the player
     * @return true if the player is the same as the one who is connected with this client instance
     */
    private boolean isMine(String username){
        return username.equals(myUsername);
    }
}
