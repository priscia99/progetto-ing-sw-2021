package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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
    @FXML
    private Pane strongboxCoinIco, strongboxServantIco, strongboxShieldIco, strongboxStoneIco;
    @FXML
    private Pane productionPane;
    @FXML
    private Pane topDeckCard1, topDeckCard2, topDeckCard3, productionLeader1, productionLeader2;;
    @FXML
    private Button confirmProductionButton, cancelProductionButton;
    @FXML
    private Pane genericInput1, genericInput2, genericOutput, genericLeader1, genericLeader2;
    @FXML
    private Pane productionCoinIcon, productionShieldIcon, productionStoneIcon, productionServantIcon;
    @FXML
    private Pane leader1depot1, leader1depot2, leader2depot1, leader2depot2;
    @FXML
    private AnchorPane chooseMarbleToConvertPane;
    @FXML
    private Pane resourceToConvert1, resourceToConvert2;
    @FXML
    private Pane popeFavor1, popeFavor2, popeFavor3;    // pope favors pane

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
    PickResourcesFromStorageController pickResourcesFromStorageController;
    ProductionController productionController;
    ChooseConversionController chooseConversionController;

    // Client controller
    ClientController clientController;

    public MainGUIController(){}    // default constructor needed by FXMLLoader class

    @FXML
    public void initialize(){
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

    /**
     * Init the Graphic User Interface by initialazing all GUI controllers
     * @param controller Client controller
     */
    public void initGUI(ClientController controller) {
        clientController = controller;
        statsController = new StatsController(clientController, statsPane);
        leaderCardsController = new LeaderCardsController(clientController, leaderCardsPane, leaderZoomPane, leaderCardZoomGrid, leaderCardZoomImage, leader1depot1, leader1depot2, leader2depot1, leader2depot2);
        faithPathController = new FaithPathController(clientController, faithPathPane, popeFavor1, popeFavor2, popeFavor3);
        warehouseController = new WarehouseController(clientController, firstDepot, secondDepot, thirdDepot, swapDepotsMenu, dropResource);
        strongBoxController = new StrongBoxController(clientController, strongboxPane, strongboxCoin, strongboxServant, strongboxShield, strongboxStone, strongboxCoinIco, strongboxServantIco, strongboxShieldIco, strongboxStoneIco);
        developmentCardsController = new DevelopmentCardsController(clientController, firstDevSlot, secondDevSlot, thirdDevSlot, produceButton);
        marbleMarketController = new MarbleMarketController(clientController, marbleMarketPane, notForSaleMarble, tabPane, getMarbleMarketButtons());
        developmentCardMarketController = new DevelopmentCardMarketController(clientController, developmentCardsMarketPane, devCardZoomPane, devCardZoomGrid, devCardZoomImage);
        chooseLeadersController = new ChooseLeadersController(clientController, chooseLeadersPane, confirmLeadersButton);
        chooseResourcesController = new ChooseResourcesController(clientController, chooseResourcesPane, chooseResourcesLabel, this.getChooseResourcesIcons(), this.getChooseResourcesButtons());
        playerBoardController = new PlayerBoardController(clientController, playerSelector, playerBoardLabel);
        endTurnButtonController = new EndTurnButtonController(clientController, endTurnButton);
        pickResourcesFromStorageController = new PickResourcesFromStorageController(clientController, confirmChoiceButton);
        productionController = new ProductionController(clientController, productionPane, getProductionCardsPanes(), getGenericProductionPanes(), getProductionIcons(), confirmProductionButton, cancelProductionButton);
        chooseConversionController = new ChooseConversionController(clientController, chooseMarbleToConvertPane, resourceToConvert1, resourceToConvert2);
    }

    private Map<String, Pane> getProductionCardsPanes(){
        Map<String, Pane> cardsPanes = new HashMap<>();
        cardsPanes.put("dev-card-1", topDeckCard1);
        cardsPanes.put("dev-card-2", topDeckCard2);
        cardsPanes.put("dev-card-3", topDeckCard3);
        cardsPanes.put("leader-card-1", productionLeader1);
        cardsPanes.put("leader-card-2", productionLeader2);
        return cardsPanes;
    }

    private Map<String, Pane> getGenericProductionPanes(){
        Map<String, Pane> genericPanes = new HashMap<>();
        genericPanes.put("generic-input-1", genericInput1);
        genericPanes.put("generic-input-2", genericInput2);
        genericPanes.put("generic-output", genericOutput);
        genericPanes.put("generic-leader-1", genericLeader1);
        genericPanes.put("generic-leader-2", genericLeader2);
        return genericPanes;
    }

    private Map<String, Pane> getProductionIcons(){
        Map<String, Pane> iconPanes = new HashMap<>();
        iconPanes.put("production-coin", productionCoinIcon);
        iconPanes.put("production-shield", productionShieldIcon);
        iconPanes.put("production-servant", productionServantIcon);
        iconPanes.put("production-stone", productionStoneIcon);
        return iconPanes;
    }

    public void startingTurn(){
        getDevelopmentCardsController().setProduceButtonEnable(true);
        getWarehouseController().setSwapMenuEnable(true);
    }

    /**
     * Initializes and returns map with all marble market arrows (in order to choose resources from market)
     * @return A map with all marble market buttons
     */
    private Map<String, Button> getMarbleMarketButtons(){
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

    /**
     * Initializes and returns a map with all resources icons (in order to choose initial resources)
     * @return A map with all resources icons
     */
    public Map<String, Pane> getChooseResourcesIcons(){
        Map<String, Pane> iconsMap = new HashMap<>();
        iconsMap.put("coin-icon", chooseCoinIcon);
        iconsMap.put("shield-icon", chooseShieldIcon);
        iconsMap.put("stone-icon", chooseStoneIcon);
        iconsMap.put("servant-icon", chooseServantIcon);
        return iconsMap;
    }

    /**
     * Initializes and returns a map with all destinations button (in order to choose where to put initial resources)
     * @return A map with all resources destinations
     */
    public Map<String, Button> getChooseResourcesButtons(){
        Map<String, Button> buttonsMap = new HashMap<>();
        buttonsMap.put("firstdepot-button", firstDepotButton);
        buttonsMap.put("seconddepot-button", secondDepotButton);
        buttonsMap.put("thirddepot-button", thirdDepotButton);
        buttonsMap.put("confirmresources-button", confirmResourcesButton);
        return buttonsMap;
    }

    /**
     * Disable all possible actions if it's not the user's turn
     */
    public void disableAllActions() {
        // TODO complete with other actions to disable
        warehouseController.setSwapMenuEnable(false);
        developmentCardsController.setProduceButtonEnable(false);
        leaderCardsController.disableLeaderCardsHandlers();
        leaderCardsController.setCanUserDoAction(false);
        endTurnButtonController.enableEndTurn(false);
    }

    /**
     * Enable all possible actions if it's the user's turn
     */
    public void enableAllActions(){
        marbleMarketController.enableHandlers();
        leaderCardsController.setCanUserDoAction(true);
        leaderCardsController.enableLeaderCardsHandlers();
        endTurnButtonController.enableEndTurn(true);
    }

    /**
     * When main action is chosen, enable the main action state by displaying the user's playerboard
     * @param toShow
     */
    public void enableMainActionState(String toShow){
        if(clientController.getGame().getMyUsername().equals(clientController.getGame().getCurrentPlayer())){
            if(!clientController.getGame().isMainActionDone()){
                endTurnButtonController.enableEndTurn(false);
                clientController.displayPlayerboardByUsername(toShow);
                playerBoardController.disableChangePlayer();
            }
        }
    }

    /**
     * When a main action is done, make all other playerboards visible
     */
    public void disableMainActionState(){
        endTurnButtonController.enableEndTurn(true);
        playerBoardController.enableChangePlayer();
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

    public EndTurnButtonController getEndTurnButtonController() {
        return endTurnButtonController;
    }

    public PickResourcesFromStorageController getPickResourcesFromStorageController() {
        return pickResourcesFromStorageController;
    }

    public ProductionController getProductionController() {
        return productionController;
    }

    public ChooseConversionController getChooseConversionController(){return chooseConversionController;}
}
