package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import it.polimi.ingsw.client.model.ClientPlayerBoard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.w3c.dom.Text;

import java.awt.event.ItemListener;
import java.util.ArrayList;

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
    private GridPane developmentCardsPane;                  // development cards pane
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

    // Secondary controllers
    StatsController statsController;
    LeaderCardsController leaderCardsController;
    PlayerBoardController playerBoardController;
    MarbleMarketController marbleMarketController;
    DevelopmentCardMarketController developmentCardMarketController;
    DevelopmentCardsController developmentCardsController;
    FaithPathController faithPathController;
    WarehouseController warehouseController;
    StrongBoxController strongBoxController;

    public MainGUIController(){}    // default constructor needed by FXMLLoader class

    @FXML
    public void initialize(){
        // controllers init
        statsController = new StatsController(statsPane);
        leaderCardsController = new LeaderCardsController(leaderCardsPane);
        faithPathController = new FaithPathController(faithPathPane);
        warehouseController = new WarehouseController(firstDepot, secondDepot, thirdDepot, swapDepotsMenu);
        strongBoxController = new StrongBoxController(strongboxPane, strongboxCoin, strongboxServant, strongboxShield, strongboxStone);
        developmentCardsController = new DevelopmentCardsController(firstDevSlot, secondDevSlot, thirdDevSlot);
        marbleMarketController = new MarbleMarketController(marbleMarketPane, notForSaleMarble);
    }

    public void work(){
        System.out.println(statsPane);
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
    public PlayerBoardController getPlayerBoardController() {
        return playerBoardController;
    }
    public MarbleMarketController getMarbleMarketController() {
        return marbleMarketController;
    }
    public DevelopmentCardMarketController getDevelopmentCardMarketController() { return developmentCardMarketController; }
    public FaithPathController getFaithPathController(){
        return faithPathController;
    }
}
