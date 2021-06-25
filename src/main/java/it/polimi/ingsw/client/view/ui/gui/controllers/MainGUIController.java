package it.polimi.ingsw.client.view.ui.gui.controllers;

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
    private GridPane firstDepot, secondDepot, thirdDepot;   // warehouse panes
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

        statsController = new StatsController(statsPane);
        leaderCardsController = new LeaderCardsController(leaderCardsPane);
        faithPathController = new FaithPathController(faithPathPane);
        warehouseController = new WarehouseController(firstDepot, secondDepot, thirdDepot, swapDepotsMenu);
        strongBoxController = new StrongBoxController(strongboxPane, strongboxCoin, strongboxServant, strongboxShield, strongboxStone);
        developmentCardsController = new DevelopmentCardsController(firstDevSlot, secondDevSlot, thirdDevSlot, produceButton);
        marbleMarketController = new MarbleMarketController(marbleMarketPane, notForSaleMarble, tabPane);
        developmentCardMarketController = new DevelopmentCardMarketController(developmentCardsMarketPane);
        chooseLeadersController = new ChooseLeadersController(chooseLeadersPane, confirmLeadersButton);
        chooseResourcesController = new ChooseResourcesController(chooseResourcesPane, chooseResourcesLabel, this.getChooseResourcesIcons(), this.getChooseResourcesButtons());
        playerBoardController = new PlayerBoardController(playerSelector, playerBoardLabel);
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

    public void initGUI(){
        // TODO fix init GUI
        /*
        strongBoxController.refreshStrongbox(new ClientStrongbox());
        warehouseController.refreshWarehouse(new ClientWarehouse());
        */
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
}
