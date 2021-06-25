package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientWarehouse;
import it.polimi.ingsw.client.view.representation.RepresentationBuilder;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.server.model.card.effect.ChangeEffect;
import it.polimi.ingsw.server.model.card.effect.DepotEffect;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.player_board.storage.Warehouse;
import it.polimi.ingsw.server.model.resource.ResourceDepot;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceType;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Objects;

public class WarehouseController extends GenericGUIController {

    private static final String COIN_PATH = "/img/ico/coin.png";
    private static final String SERVANT_PATH = "/img/ico/servant.png";
    private static final String SHIELD_PATH = "/img/ico/shield.png";
    private static final String STONE_PATH = "/img/ico/stone.png";

    private GridPane firstDepot;
    private GridPane secondDepot;
    private GridPane thirdDepot;
    private MenuButton swapDepotsMenu;
    private ArrayList<ObservableList<Node>> warehouseElements;
    private ClientWarehouse activeWarehouse;
    private Marble marbleToInsert;
    private ArrayList<ResourcePosition> positions = null;
    private ArrayList<ResourceType> conversions = null;
    int insertPositionIndex;
    private ArrayList<Marble> selected;
    private ArrayList<ChangeEffect> changeEffects;
    private ArrayList<DepotEffect> depotEffects;

    public WarehouseController(ClientController clientController, GridPane firstDepot, GridPane secondDepot, GridPane thirdDepot, MenuButton swapDepotsMenu) {
        super(clientController);
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
        super.getClientController().swapDepots(1,2);
    };

    private final EventHandler<ActionEvent> swap1and3 = event -> {
        super.getClientController().swapDepots(1,3);
    };

    private final EventHandler<ActionEvent> swap2and3 = event -> {
        super.getClientController().swapDepots(2,3);
    };

    /**
     * Refreshes warehouse pane with given warehouse
     * @param warehouse Player's warehouse to show
     */
    public void refreshWarehouse(ClientWarehouse warehouse, boolean isMine){
        this.activeWarehouse = warehouse;
        this.setSwapMenuEnable(isMine);
        if(activeWarehouse.isInitialized()){
            // iterate through depots
            for(int i=0; i<3; i++){
                ResourceDepot tempDepot = activeWarehouse.getResourceDepot(i);
                // iterate through positions in depots
                for(int j=0; j<tempDepot.getCapacity(); j++){
                    Pane resourcePane = (Pane) warehouseElements.get(i).get(j);
                    if(j<tempDepot.getQuantity()) {
                        // if the position is an actual stored resource set a backround image
                        String iconPath = getPathByResourceType(activeWarehouse.getResourceDepot(i).getResourceType());
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

    public void insertResourcesToDepot(MarbleSelection selection, ArrayList<Marble> selected, ArrayList<ChangeEffect> changeEffects, ArrayList<DepotEffect> depotEffects){
       positions = new ArrayList<>();
       conversions = new ArrayList<>();
       this.selected = selected;
       this.changeEffects = changeEffects;
       this.depotEffects = depotEffects;
       insertPositionIndex = 0;
       parseNextPosition(insertPositionIndex);
        try{
            for(Marble marble : selected){
                if(marble.getResourceType().equals(ResourceType.FAITH)) continue;
                if(marble.getResourceType().equals(ResourceType.BLANK) && !changeEffects.isEmpty() ){
                    // TODO ask for discounts in GUI
                    // conversions.add(askForConversions(changeEffects));
                }
                if(!marble.getResourceType().equals(ResourceType.BLANK) ||
                        marble.getResourceType().equals(ResourceType.BLANK) && !changeEffects.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Insert position in which you want to add " + marble.getResourceType().toString());
                    alert.show();
                    // TODO manage leader card depots
                    askForDestinationDepot(marble);
                    // displayPossibleResourcePositions(depotEffects);
                }
            }
            // controller.pickResources(selection, positions, conversions);
        } catch(Exception e){
            // boh
        }
    }

    private void parseNextPosition(int index){
        if(index >= selected.size()){
            // mandarlo al controller
            return;
        }
        Marble marble = selected.get(index);
        if(marble.getResourceType().equals(ResourceType.FAITH)) return;
        if(marble.getResourceType().equals(ResourceType.BLANK) && !changeEffects.isEmpty() ){
            // TODO ask for discounts in GUI
            // conversions.add(askForConversions(changeEffects));
        }
        if(!marble.getResourceType().equals(ResourceType.BLANK) ||
                marble.getResourceType().equals(ResourceType.BLANK) && !changeEffects.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Insert position in which you want to add " + marble.getResourceType().toString());
            alert.show();
            // TODO manage leader card depots
            askForDestinationDepot(marble);
            // displayPossibleResourcePositions(depotEffects);
        }
    }

    public void askForDestinationDepot(Marble marble){
        this.marbleToInsert = marble;
        this.setSwapMenuEnable(false);
        if(activeWarehouse.isInitialized()){
            // iterate through depots
            for(int i=0; i<3; i++){
                ResourceDepot tempDepot = activeWarehouse.getResourceDepot(i);
                // iterate through positions in depots
                for(int j=0; j<tempDepot.getCapacity(); j++){
                    Pane resourcePane = (Pane) warehouseElements.get(i).get(j);
                    if(j<tempDepot.getQuantity()) {
                        // if the position is an actual stored resource set a backround image
                        String iconPath = getPathByResourceType(activeWarehouse.getResourceDepot(i).getResourceType());
                        resourcePane.setStyle("-fx-background-image: url(" + iconPath + ");");
                    }
                    else {
                        // if the position is free set an empty background (no image)
                        resourcePane.setStyle("-fx-background-image: url('/img/ico/zero.png');");
                        resourcePane.addEventHandler(MouseEvent.MOUSE_CLICKED, onMarbleDestinationChosen);
                    }
                }
            }
        }
        else{
            // warehouse is not initialized yet
            for(ObservableList<Node> nodeList : warehouseElements){
                for(Node node : nodeList){
                    Pane resourcePane = ((Pane)node);
                    resourcePane.setStyle("-fx-background-image: url('/img/ico/zero.png');");
                    resourcePane.addEventHandler(MouseEvent.MOUSE_CLICKED, onMarbleDestinationChosen);
                }
            }
        }
    }

    private final EventHandler<MouseEvent> onMarbleDestinationChosen = event -> {
        Pane triggeredPane = (Pane) event.getSource();
        String iconPath = this.getPathByResourceType(marbleToInsert.getResourceType());
        triggeredPane.setStyle("-fx-background-image: url(" + iconPath + ");");
        triggeredPane.setEffect(new Glow(0.6));
        // positions.add(parseResourcePosition(positionRaw));
        parseNextPosition(++insertPositionIndex);
    };

    public String getPathByResourceType(ResourceType type){
        String iconPath = null;
        switch (type) {
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
        return iconPath;
    }

}
