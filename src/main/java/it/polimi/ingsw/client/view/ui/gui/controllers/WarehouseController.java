package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientWarehouse;
import it.polimi.ingsw.client.view.representation.RepresentationBuilder;
import it.polimi.ingsw.server.model.resource.ResourceDepot;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Objects;

public class WarehouseController {

    private static final String COIN_PATH = "/img/ico/coin.png";
    private static final String SERVANT_PATH = "/img/ico/servant.png";
    private static final String SHIELD_PATH = "/img/ico/shield.png";
    private static final String STONE_PATH = "/img/ico/stone.png";
    private ClientController clientController;

    private GridPane firstDepot;
    private GridPane secondDepot;
    private GridPane thirdDepot;
    private MenuButton swapDepotsMenu;
    private ArrayList<ObservableList<Node>> warehouseElements;

    public WarehouseController(GridPane firstDepot, GridPane secondDepot, GridPane thirdDepot, MenuButton swapDepotsMenu) {
        this.swapDepotsMenu = swapDepotsMenu;
        warehouseElements = new ArrayList<>();
        this.firstDepot = firstDepot;
        this.secondDepot = secondDepot;
        this.thirdDepot = thirdDepot;
        warehouseElements.add(firstDepot.getChildren());
        warehouseElements.add(secondDepot.getChildren());
        warehouseElements.add(thirdDepot.getChildren());
        this.initSwapSelector();
    }

    private void initSwapSelector() {
        MenuItem tempItem;

        tempItem = new MenuItem("Swap 1 and 2");
        tempItem.setOnAction(swap1and2);
        swapDepotsMenu.getItems().add(tempItem);

        tempItem = new MenuItem("Swap 1 and 3");
        tempItem.setOnAction(swap1and3);
        swapDepotsMenu.getItems().add(tempItem);

        tempItem = new MenuItem("Swap 2 and 3");
        tempItem.setOnAction(swap2and3);
        swapDepotsMenu.getItems().add(tempItem);
    }

    private final EventHandler<ActionEvent> swap1and2 = event -> {
        clientController.swapDepots(1,2);
    };

    private final EventHandler<ActionEvent> swap1and3 = event -> {
        clientController.swapDepots(1,3);
    };

    private final EventHandler<ActionEvent> swap2and3 = event -> {
        clientController.swapDepots(2,3);
    };

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    /**
     * Refreshes warehouse pane with given warehouse
     * @param warehouse Player's warehouse to show
     */
    public void refreshWarehouse(ClientWarehouse warehouse, boolean isMine){
        this.setSwapMenuEnable(isMine);
        if(warehouse.isInitialized()){
            // iterate through depots
            for(int i=0; i<3; i++){
                ResourceDepot tempDepot = warehouse.getResourceDepot(i);
                // iterate through positions in depots
                for(int j=0; j<tempDepot.getCapacity(); j++){
                    Pane resourcePane = (Pane) warehouseElements.get(i).get(j);
                    if(j<tempDepot.getQuantity()) {
                        // if the position is an actual stored resource set a backround image
                        String iconPath = null;
                        switch (warehouse.getResourceDepot(i).getResourceType()) {
                            case COIN:
                                iconPath = COIN_PATH;
                                break;
                            case SERVANT:
                                iconPath = SERVANT_PATH;
                                break;
                            case SHIELD:
                                iconPath = SHIELD_PATH;
                                break;
                            case STONE:
                                iconPath = STONE_PATH;
                                break;
                        }
                        resourcePane.setStyle("-fx-background-image: url(" + iconPath + ");");
                    }
                    else {
                        // if the position is free set an empty background (no image)
                        resourcePane.setStyle("-fx-background-image: none;");
                    }
                }
            }
        }
        else{
            // warehouse is not initialized yet
            for(ObservableList<Node> nodeList : warehouseElements){
                for(Node node : nodeList){
                    ((Pane)node).setStyle("-fx-background-image: none;");
                }
            }
        }
    }

    public void setSwapMenuEnable(boolean isEnable){
        swapDepotsMenu.setDisable(!isEnable);
    }

}
