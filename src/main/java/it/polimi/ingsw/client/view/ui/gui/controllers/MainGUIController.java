package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import it.polimi.ingsw.client.model.ClientPlayerBoard;
import it.polimi.ingsw.client.model.ClientStrongbox;
import it.polimi.ingsw.client.model.ClientWarehouse;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.w3c.dom.Text;

import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainGUIController {

    // FXML Elements
    @FXML
    private GridPane statsPane;                             // statistics pane
    @FXML
    private MenuButton playersChoicePicker;                 // player choice selector
    @FXML
    private GridPane firstDepot, secondDepot, thirdDepot;   // warehouse panes
    @FXML
    private MenuButton swapDepotsMenu;                      // swap depots selector
    @FXML
    private GridPane strongboxPane;                         // strongbox pane
    @FXML
    TextField strongboxCoin, strongboxServant, strongboxShield, strongboxStone; // strongbox elements
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

    // plus-minus buttons in choose resources screen
    @FXML
    private Button stonePlusButton, stoneMinusButton, shieldPlusButton, shieldMinusButton, servantPlusButton, servantMinusButton, coinPlusButton, coinMinusButton;


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

    public MainGUIController(){}    // default constructor needed by FXMLLoader class

    @FXML
    public void initialize(){
        // TODO implement development card market controller
        // controllers init

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab t1) {
                        marbleMarketController.refreshMarbleMarket();
                        developmentCardMarketController.refreshCardsMarket();
                        tabPane.requestLayout();
                    }
                }
        );

        statsController = new StatsController(statsPane);
        leaderCardsController = new LeaderCardsController(leaderCardsPane);
        faithPathController = new FaithPathController(faithPathPane);
        warehouseController = new WarehouseController(firstDepot, secondDepot, thirdDepot, swapDepotsMenu);
        strongBoxController = new StrongBoxController(strongboxPane, strongboxCoin, strongboxServant, strongboxShield, strongboxStone);
        developmentCardsController = new DevelopmentCardsController(firstDevSlot, secondDevSlot, thirdDevSlot);
        marbleMarketController = new MarbleMarketController(marbleMarketPane, notForSaleMarble, tabPane);
        developmentCardMarketController = new DevelopmentCardMarketController(developmentCardsMarketPane);
        chooseLeadersController = new ChooseLeadersController(chooseLeadersPane, confirmLeadersButton);
        chooseResourcesController = new ChooseResourcesController(chooseResourcesPane, chooseResourcesLabel, this.createButtonsMap());
    }

    public Map<String, Button> createButtonsMap(){
        Map<String, Button> buttonsMap = new HashMap<>();
        buttonsMap.put("coin-plus", coinPlusButton);
        buttonsMap.put("coin-minus", coinMinusButton);
        buttonsMap.put("shield-plus", shieldPlusButton);
        buttonsMap.put("shield-minus", shieldMinusButton);
        buttonsMap.put("servant-plus", servantPlusButton);
        buttonsMap.put("servant-minus", servantMinusButton);
        buttonsMap.put("stone-plus", stonePlusButton);
        buttonsMap.put("stone-minus", stoneMinusButton);
        return buttonsMap;
    }

    public void initGUI(){
        strongBoxController.refreshStrongbox(new ClientStrongbox());
        warehouseController.refreshWarehouse(new ClientWarehouse());
    }
    /**
     * Initialize the playerboard choice picker
     * @param players
     */
    public void initMenuChoicePicker(ArrayList<String> players){
        players.stream().forEach(playerName -> playersChoicePicker.getItems().add(new MenuItem(playerName)));
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
}
