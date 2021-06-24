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
    public void displayGameStarted() {
        primaryStage.getScene().lookup("#join-button").setDisable(true);
        primaryStage.getScene().lookup("#create-button").setDisable(true);
        Platform.runLater(() -> SceneController.displayPopupMessage(primaryStage, "Game has started!"));
    }

    @Override
    public void displayNewTurn(String player, Boolean myTurn) {
        if(myTurn){
            Platform.runLater(() -> {
                SceneController.displayPopupMessage(primaryStage, player + ", it's your turn!");
            });
        }
    }

    @Override
    public void displayLeaderCard(ClientLeaderCard clientLeaderCard) {

    }

    @Override
    public void displayChooseInitialResourcesMenu(int toChoose) {
        Platform.runLater(() -> {
            SceneController.getMainGUIController().getChooseResourcesController().setClientController(controller);
            SceneController.getMainGUIController().getChooseResourcesController().activeScreen(toChoose);
        });
    }

    @Override
    public void displayInitialLeadersMenu(ArrayList<String> cardsIDs) {
        Platform.runLater(() -> {
            SceneController.getMainGUIController().getChooseLeadersController().setCurrentController(controller);
            SceneController.getMainGUIController().getChooseLeadersController().activeScreen(cardsIDs);
        });
    }


    @Override
    public void displayWarehouse(ClientWarehouse warehouse) {
        Platform.runLater(() -> SceneController.getMainGUIController().getWarehouseController().refreshWarehouse(warehouse));
    }

    /**
     * Refreshes Leader cards deck view in GUI
     * @param deck Player leader cards
     */
    @Override
    public void displayLeaderCardDeck(ClientLeaderCardDeck deck) {
        Platform.runLater(() -> SceneController.getMainGUIController().getLeaderCardsController().refreshLeaderCards(deck));
    }

    @Override
    public void startListening() {
    }

    @Override
    public void displayError(String error) {
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
    public void displayStrongBox(ClientStrongbox strongbox) {
        Platform.runLater(() -> SceneController.getMainGUIController().getStrongBoxController().refreshStrongbox(strongbox));
    }

    @Override
    public void displayFaithPath(ClientFaithPath path) {
        Platform.runLater(() -> {SceneController.getMainGUIController().getFaithPathController().refreshFaithPath(path);});
    }

    @Override
    public void displayDevelopmentCardDecks(ClientDevelopmentCardDecks deck) {
        Platform.runLater(() -> {SceneController.getMainGUIController().getDevelopmentCardsController().refreshDevelopmentCards(deck);});
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
    public void displayPickResourceMenu(MarbleSelection selection, ArrayList<Marble> selected, ArrayList<ChangeEffect> changeEffects, ArrayList<DepotEffect> depotEffects) {

    }

    @Override
    public void displayBuyDevelopmentCardMenu(String id, ArrayList<DiscountEffect> discounts, ArrayList<DepotEffect> depotEffects) {

    }

    @Override
    public void displayProduceMenu(ArrayList<ProductionEffect> effects, ArrayList<DepotEffect> depotEffects) {

    }

    @Override
    public void startUI(ClientGame game) {
        Platform.runLater(() -> SceneController.showGameScene(primaryStage));
        Platform.runLater(() -> {
            SceneController.getMainGUIController().initGUI();
            SceneController.getMainGUIController().initMenuChoicePicker(new ArrayList<>(game.getPlayerBoardMap().keySet()));
        });
    }

    @Override
    public void displayUserStats(ClientPlayerBoard playerBoard) {
        Platform.runLater(() -> {
            SceneController.getMainGUIController().getStatsController().refreshStats(playerBoard);
        });
    }

}
