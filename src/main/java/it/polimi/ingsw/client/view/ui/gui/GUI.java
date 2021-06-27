package it.polimi.ingsw.client.view.ui.gui;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.client.view.ui.gui.controllers.MainGUIController;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.network.auth_data.AuthData;
import it.polimi.ingsw.server.model.card.effect.ChangeEffect;
import it.polimi.ingsw.server.model.card.effect.DepotEffect;
import it.polimi.ingsw.server.model.card.effect.DiscountEffect;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceType;
import it.polimi.ingsw.utils.CustomLogger;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class GUI implements UI{
    private ClientController controller;    // reference to client controller
    private Stage primaryStage;             // GUI Application primary stage
    private String selectedPlayerBoard = null;
    private String myUsername = null;

    public void loadAuthScreen(Client client){
        Platform.runLater(() -> SceneController.requestAuth(primaryStage, client));
    }

    @Override
    public AuthData requestAuth() {return null;}

    public GUI(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    @Override
    public void setController(ClientController controller) {
        this.controller = controller;
    }

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

    @Override
    public void displayLobbyCreated(String lobbyId) {
        primaryStage.getScene().lookup("#join-button").setDisable(true);
        primaryStage.getScene().lookup("#create-button").setDisable(true);
        Platform.runLater(() -> ((TextField)primaryStage.getScene().lookup("#login-message")).setText("Created lobby " + lobbyId));
    }

    @Override
    public void displayLobbyJoined(String lobbyId) {
        primaryStage.getScene().lookup("#join-button").setDisable(true);
        primaryStage.getScene().lookup("#create-button").setDisable(true);
        Platform.runLater(() -> ((TextField)primaryStage.getScene().lookup("#login-message")).setText("Joined lobby " + lobbyId));
    }

    @Override
    public void displayNewTurn(String player, Boolean myTurn) {
        if(myTurn){
            Platform.runLater(() -> {
                // TODO enable all actions
                // SceneController.getMainGUIController().enableAllActions();
                SceneController.getMainGUIController().enableAllActions();
                SceneController.displayPopupMessage(primaryStage, player + ", it's your turn!");
            });
        }else{
            Platform.runLater(() -> {
                SceneController.getMainGUIController().disableAllActions();
            });
        }
    }

    @Override
    public void displayChooseInitialResourcesMenu(int toChoose) {
        Platform.runLater(() -> {
            SceneController.getMainGUIController().getChooseResourcesController().activeScreen(toChoose);
        });
    }

    @Override
    public void displayInitialLeadersMenu(ArrayList<String> cardsIDs) {
        Platform.runLater(() -> {
            SceneController.getMainGUIController().getChooseLeadersController().activeScreen(cardsIDs);
        });
    }


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

    @Override
    public void startListening() {
    }

    @Override
    public void displayError(String error) {
        CustomLogger.getLogger().severe(error);
        Platform.runLater(() -> SceneController.displayPopupError(primaryStage, error));
    }

    @Override
    public void displayInfo(String info) {
        Platform.runLater(() -> SceneController.displayPopupMessage(primaryStage, info));
    }

    @Override
    public void displayMarbleMarket(ClientMarbleMarket market) {
        Platform.runLater(() -> SceneController.getMainGUIController().getMarbleMarketController().setMarbleMarket(market));
    }

    @Override
    public void displayCardMarket(ClientCardsMarket market) {
        Platform.runLater(() -> SceneController.getMainGUIController().getDevelopmentCardMarketController().setCardsMarket(market));
    }

    @Override
    public void displayStrongBox(ClientStrongbox strongbox, String username) {
        if (isToRefresh(username)) {
            Platform.runLater(() -> SceneController.getMainGUIController().getStrongBoxController().refreshStrongbox(strongbox));
        }
    }

    @Override
    public void displayFaithPath(ClientFaithPath path, String username) {
        if (isToRefresh(username)) {
            Platform.runLater(() -> {
                SceneController.getMainGUIController().getFaithPathController().refreshFaithPath(path);
                SceneController.getMainGUIController().getStatsController().refreshFaithPoints(path.getFaithPoints(), username);
            });
        }
    }

    @Override
    public void displayDevelopmentCardDecks(ClientDevelopmentCardDecks deck, String username) {
        if (isToRefresh(username)) {
            Platform.runLater(() -> {
                SceneController.getMainGUIController().getDevelopmentCardsController().refreshDevelopmentCards(deck, isMine(username));
                SceneController.getMainGUIController().getStatsController().refreshDevelopmentCardNumbers(deck.getCardsNumber(), username);
            });
        }
    }

    @Override
    public void displayOtherPlayersUsername(ArrayList<String> usernames) {

    }

    @Override
    public void displayHelpMessage() {

    }

    @Override
    public void displayTurnInfo(ArrayList<String> names, String current) {

    }

    @Override
    public void displayPossibleActions(boolean myTurn, boolean mainActionDone) {

    }

    @Override
    public void displayPickResourceMenu(MarbleSelection selection, ArrayList<Marble> selected, ArrayList<ClientLeaderCard> changeEffects, ArrayList<ClientLeaderCard> depotEffects) {
        SceneController.getMainGUIController().getWarehouseController().insertResourcesToDepot(selection, selected, changeEffects, depotEffects);
    }

    @Override
    public void displayBuyDevelopmentCardMenu(String id, ArrayList<ClientLeaderCard> discounts, ArrayList<ClientLeaderCard> depotEffects) {

    }

    @Override
    public void displayProduceMenu(ArrayList<ClientLeaderCard> effects, ArrayList<ClientLeaderCard> depotEffects) {
        Platform.runLater(() -> {
            SceneController.getMainGUIController().getProductionController().openProductionSelection(effects);
        });
    }

    @Override
    public void startUI(ClientGame game) {
        this.selectedPlayerBoard = game.getMyUsername();
        this.myUsername = game.getMyUsername();
        Platform.runLater(() -> SceneController.showGameScene(primaryStage));
        Platform.runLater(() -> {
            SceneController.getMainGUIController().initGUI(controller);
            SceneController.getMainGUIController().getWarehouseController().refreshWarehouse(game.getPlayerBoardMap().get(game.getMyUsername()).getWarehouse(), true);
            SceneController.getMainGUIController().getStrongBoxController().initStrongboxScreen();
            SceneController.getMainGUIController().getPlayerBoardController().initClientSelector(new ArrayList<>(game.getPlayerBoardMap().keySet()));
            SceneController.getMainGUIController().getPlayerBoardController().setUsername(game.getMyUsername(), true);
        });
    }

    @Override
    public void displayUserStats(ClientPlayerBoard playerBoard) {
        Platform.runLater(() -> {
            SceneController.getMainGUIController().getStatsController().initStats(playerBoard);
        });
    }

    public void setPlayerBoardUsername(String username, boolean isMine){
        this.selectedPlayerBoard = username;
        Platform.runLater(() -> SceneController.getMainGUIController().getPlayerBoardController().setUsername(username, isMine));
    }

    private boolean isToRefresh(String username){
        return username.equals(selectedPlayerBoard);
    }

    private boolean isMine(String username){
        return username.equals(myUsername);
    }
}
