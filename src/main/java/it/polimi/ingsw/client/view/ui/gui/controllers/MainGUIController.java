package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientStrongbox;
import it.polimi.ingsw.client.model.ClientWarehouse;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainGUIController {

    // FXML Elements
    @FXML
    private GridPane statsPane;                             // statistics pane
    @FXML
    private MenuButton playerSelector;                 // player choice selector
    @FXML
    private GridPane firstDepot, secondDepot, thirdDepot;// warehouse panes
    @FXML
    private Button dropResource;
    @FXML
    private MenuButton swapDepotsMenu;                      // swap depots selector
    @FXML
    private GridPane strongboxPane;                         // strongbox pane
    @FXML
    private TextField strongboxCoin, strongboxServant, strongboxShield, strongboxStone; // strongbox elements
    @FXML
    private GridPane developmentCardsMarketPane;          // development cards market pane
    @FXML
    private GridPane leaderCardsPane;                       // leader cards pane
    @FXML
    private GridPane faithPathPane;                         // faith path pane
    @FXML
    private AnchorPane firstDevSlot, secondDevSlot, thirdDevSlot;   // user dev cards slot
    @FXML
    private GridPane marbleMarketPane;                      // marble market pane
    @FXML
    private Pane notForSaleMarble;                          // not for sale marble in marble market
    @FXML
    private AnchorPane chooseLeadersPane;                   // choose leaders pane
    @FXML
    private AnchorPane chooseResourcesPane;                 // choose resources pane
    @FXML
    private Button confirmLeadersButton;                    // confirm chosen leader cards button
    @FXML
    private Tab marbleMarketTab;                            // marble market tab
    @FXML
    private TabPane tabPane;
    @FXML
    private Label chooseResourcesLabel;                     // choose n resources label
    @FXML
    private Pane chooseCoinIcon, chooseServantIcon, chooseShieldIcon, chooseStoneIcon;   // icons for choose leaders
    @FXML
    private Button firstDepotButton, secondDepotButton, thirdDepotButton, confirmResourcesButton;   // choose depot buttons
    @FXML
    private Label playerBoardLabel; // playerboard label
    @FXML
    private Button produceButton;   // produce button
    @FXML
    private AnchorPane leaderZoomPane;  // leader zoom pane
    @FXML
    private Pane leaderCardZoomImage;   // leader card zoom image
    @FXML
    private GridPane leaderCardZoomGrid;    // leader card zoom grid
    @FXML
    private Button marketBtnC1, marketBtnC2, marketBtnC3, marketBtnC4, marketBtnR1, marketBtnR2, marketBtnR3;   // marble market buttons
    @FXML
    private Button endTurnButton;   // end turn button
    @FXML
    private Button confirmChoiceButton; // confirm choice button
    @FXML
    private Pane devCardZoomPane;   // dev cards zoom pane
    @FXML
    private Pane devCardZoomImage;
    @FXML
    private GridPane devCardZoomGrid;   // dev cards zoom grid

    // Secondary controllers
    StatsController statsController;
    LeaderCardsController leaderCardsController;
    MarbleMarketController marbleMarketController;
    DevelopmentCardMarketController developmentCardMarketController;
    DevelopmentCardsController developmentCardsController;
    FaithPathController faithPathController;
    WarehouseController warehouseController;
    StrongBoxController strongBoxController;
    ChooseLeadersController chooseLeadersController;
    ChooseResourcesController chooseResourcesController;
    PlayerBoardController playerBoardController;
    EndTurnButtonController endTurnButtonController;

    // Client controller
    ClientController clientController;

    public MainGUIController(){}    // default constructor needed by FXMLLoader class

    @FXML
    public void initialize(){
        // TODO implement development card market controller
        // controllers init
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab t1) {
                        tabPane.requestLayout();
                    }
                }
        );

    }

    public void initGUI(ClientController controller) {
        this.clientController = controller;
        statsController = new StatsController(clientController, statsPane);
        leaderCardsController = new LeaderCardsController(clientController, leaderCardsPane, leaderZoomPane, leaderCardZoomGrid, leaderCardZoomImage);
        faithPathController = new FaithPathController(clientController, faithPathPane);
        warehouseController = new WarehouseController(clientController, firstDepot, secondDepot, thirdDepot, swapDepotsMenu, dropResource);
        strongBoxController = new StrongBoxController(clientController, strongboxPane, strongboxCoin, strongboxServant, strongboxShield, strongboxStone);
        developmentCardsController = new DevelopmentCardsController(clientController, firstDevSlot, secondDevSlot, thirdDevSlot, produceButton);
        marbleMarketController = new MarbleMarketController(clientController, marbleMarketPane, notForSaleMarble, tabPane, getMarbleMarketButtons());
        developmentCardMarketController = new DevelopmentCardMarketController(clientController, developmentCardsMarketPane, devCardZoomPane, devCardZoomGrid, devCardZoomImage);
        chooseLeadersController = new ChooseLeadersController(clientController, chooseLeadersPane, confirmLeadersButton);
        chooseResourcesController = new ChooseResourcesController(clientController, chooseResourcesPane, chooseResourcesLabel, this.getChooseResourcesIcons(), this.getChooseResourcesButtons());
        playerBoardController = new PlayerBoardController(clientController, playerSelector, playerBoardLabel);
        endTurnButtonController = new EndTurnButtonController(clientController, endTurnButton);
    }

    public Map<String, Button> getMarbleMarketButtons(){
        Map<String, Button> buttonsMap = new HashMap<>();
        buttonsMap.put("btn-c-1", marketBtnC1);
        buttonsMap.put("btn-c-2", marketBtnC2);
        buttonsMap.put("btn-c-3", marketBtnC3);
        buttonsMap.put("btn-c-4", marketBtnC4);
        buttonsMap.put("btn-r-1", marketBtnR1);
        buttonsMap.put("btn-r-2", marketBtnR2);
        buttonsMap.put("btn-r-3", marketBtnR3);
        return buttonsMap;
    }

    public Map<String, Pane> getChooseResourcesIcons(){
        Map<String, Pane> iconsMap = new HashMap<>();
        iconsMap.put("coin-icon", chooseCoinIcon);
        iconsMap.put("shield-icon", chooseShieldIcon);
        iconsMap.put("stone-icon", chooseStoneIcon);
        iconsMap.put("servant-icon", chooseServantIcon);
        return iconsMap;
    }

    public Map<String, Button> getChooseResourcesButtons(){
        Map<String, Button> buttonsMap = new HashMap<>();
        buttonsMap.put("firstdepot-button", firstDepotButton);
        buttonsMap.put("seconddepot-button", secondDepotButton);
        buttonsMap.put("thirddepot-button", thirdDepotButton);
        buttonsMap.put("confirmresources-button", confirmResourcesButton);
        return buttonsMap;
    }

    public void disableAllActions() {
        // TODO complete with other actions to disable
        warehouseController.setSwapMenuEnable(false);
        developmentCardsController.setProduceButtonEnable(false);
        leaderCardsController.disableLeaderCardsHandlers();
        leaderCardsController.setCanUserDoAction(false);
        endTurnButtonController.enableEndTurn(false);
    }

    public void enableAllActions(){
        marbleMarketController.enableHandlers();
        leaderCardsController.setCanUserDoAction(true);
        leaderCardsController.enableLeaderCardsHandlers();
        endTurnButtonController.enableEndTurn(true);
    }

    public StatsController getStatsController() {
        return statsController;
    }

    public LeaderCardsController getLeaderCardsController() {
        return leaderCardsController;
    }

    public MarbleMarketController getMarbleMarketController() {
        return marbleMarketController;
    }

    public DevelopmentCardMarketController getDevelopmentCardMarketController() {
        return developmentCardMarketController;
    }

    public DevelopmentCardsController getDevelopmentCardsController() {
        return developmentCardsController;
    }

    public FaithPathController getFaithPathController() {
        return faithPathController;
    }

    public WarehouseController getWarehouseController() {
        return warehouseController;
    }

    public StrongBoxController getStrongBoxController() {
        return strongBoxController;
    }

    public ChooseLeadersController getChooseLeadersController() {
        return chooseLeadersController;
    }

    public ChooseResourcesController getChooseResourcesController() {
        return chooseResourcesController;
    }

    public PlayerBoardController getPlayerBoardController() {
        return playerBoardController;
    }

    public void enableMainActionState(String toShow){
        clientController.displayPlayerboardByUsername(toShow);
        playerBoardController.disableChangePlayer();
    }

    public void disableMainActionState(){
        playerBoardController.enableChangePlayer();
    }
}
