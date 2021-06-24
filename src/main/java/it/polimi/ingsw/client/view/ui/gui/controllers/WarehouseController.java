package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.model.ClientWarehouse;
import it.polimi.ingsw.client.view.representation.RepresentationBuilder;
import it.polimi.ingsw.server.model.resource.ResourceDepot;
import javafx.collections.ObservableList;
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

    GridPane firstDepot;
    GridPane secondDepot;
    GridPane thirdDepot;
    MenuButton swapDepotsMenu;
    ArrayList<ObservableList<Node>> warehouseElements;

    public WarehouseController(GridPane firstDepot, GridPane secondDepot, GridPane thirdDepot, MenuButton swapDepotsMenu) {
        warehouseElements = new ArrayList<>();
        this.firstDepot = firstDepot;
        this.secondDepot = secondDepot;
        this.thirdDepot = thirdDepot;
        warehouseElements.add(firstDepot.getChildren());
        warehouseElements.add(secondDepot.getChildren());
        warehouseElements.add(thirdDepot.getChildren());
    }

    /**
     * Refreshes warehouse pane with given warehouse
     * @param warehouse Player's warehouse to show
     */
    public void refreshWarehouse(ClientWarehouse warehouse){
        if(warehouse.isInitialized()){
            System.out.println(new RepresentationBuilder().render(warehouse));
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
            System.out.println("Free position");
            for(ObservableList<Node> nodeList : warehouseElements){
                for(Node node : nodeList){
                    ((Pane)node).setStyle("-fx-background-image: none;");
                }
            }
        }
    }

    /**
     * Show swap depots options based on current warehouse resources
     * @param warehouse Player's warehouse
     */
    public void refreshSwapDepotsOptions(ClientWarehouse warehouse){
        // FIXME handle possible options
        swapDepotsMenu.getItems().add(new MenuItem("Swap depots 1 and 2"));
        swapDepotsMenu.getItems().add(new MenuItem("Swap depots 1 and 3"));
        swapDepotsMenu.getItems().add(new MenuItem("Swap depots 2 and 3"));
    }

}
