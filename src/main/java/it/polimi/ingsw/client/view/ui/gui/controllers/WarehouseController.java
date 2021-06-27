package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
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
import it.polimi.ingsw.utils.Pair;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Map;
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
    private Button dropResourceButton;
    private ArrayList<ObservableList<Node>> warehouseElements;
    private ClientWarehouse activeWarehouse;
    private Marble marbleToInsert;
    private ArrayList<ResourcePosition> positions = null;
    private ArrayList<ResourceType> conversions = null;
    private int insertPositionIndex;
    private ArrayList<Marble> selected;
    private ArrayList<ClientLeaderCard> changeEffects;
    private ArrayList<ClientLeaderCard> depotEffects;
    private ArrayList<Pair<Integer, Integer>> occupiedCells;
    private MarbleSelection marbleSelection;
    private ResourcePosition resourcePositionToDrop;

    public WarehouseController(ClientController clientController, GridPane firstDepot, GridPane secondDepot, GridPane thirdDepot, MenuButton swapDepotsMenu, Button dropResourceButton) {
        super(clientController);
        this.swapDepotsMenu = swapDepotsMenu;
        warehouseElements = new ArrayList<>();
        this.firstDepot = firstDepot;
        this.secondDepot = secondDepot;
        this.thirdDepot = thirdDepot;
        this.resourcePositionToDrop = null;
        this.dropResourceButton = dropResourceButton;
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
        super.getClientController().swapDepots(1, 2);
    };

    private final EventHandler<ActionEvent> swap1and3 = event -> {
        super.getClientController().swapDepots(1, 3);
    };

    private final EventHandler<ActionEvent> swap2and3 = event -> {
        super.getClientController().swapDepots(2, 3);
    };

    /**
     * Refreshes warehouse pane with given warehouse
     *
     * @param warehouse Player's warehouse to show
     */
    public void refreshWarehouse(ClientWarehouse warehouse, boolean isMine) {
        System.out.println("Is mine: " + isMine);
        this.activeWarehouse = warehouse;
        this.setSwapMenuEnable(isMine);
        if (activeWarehouse.isInitialized()) {
            // iterate through depots
            for (int i = 0; i < 3; i++) {
                ResourceDepot tempDepot = activeWarehouse.getResourceDepot(i);
                // iterate through positions in depots
                for (int j = 0; j < tempDepot.getCapacity(); j++) {
                    Pane resourcePane = (Pane) warehouseElements.get(i).get(j);
                    if (j < tempDepot.getQuantity()) {
                        // if the position is an actual stored resource set a backround image
                        String iconPath = getPathByResourceType(activeWarehouse.getResourceDepot(i).getResourceType());
                        resourcePane.setStyle("-fx-background-image: url(" + iconPath + ");");
                        if(isMine) {
                            resourcePane.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResourceToDrop);
                        }else{
                            resourcePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResourceToDrop);
                        }
                    } else {
                        // if the position is free set an empty background (no image)
                        resourcePane.setStyle("-fx-background-image: none;");
                        resourcePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResourceToDrop);
                    }
                }
            }
        } else {
            // warehouse is not initialized yet
            for (ObservableList<Node> nodeList : warehouseElements) {
                for (Node node : nodeList) {
                    ((Pane) node).setStyle("-fx-background-image: none;");
                }
            }
        }
    }

    public void setSwapMenuEnable(boolean isEnable) {
        swapDepotsMenu.setDisable(!isEnable);
    }

    public void insertResourcesToDepot(MarbleSelection selection, ArrayList<Marble> selected, ArrayList<ClientLeaderCard> changeEffects, ArrayList<ClientLeaderCard> depotEffects) {
        this.marbleSelection = selection;
        this.occupiedCells = new ArrayList<>();
        this.positions = new ArrayList<>();
        this.conversions = new ArrayList<>();
        this.selected = selected;
        this.changeEffects = changeEffects;
        this.depotEffects = depotEffects;
        insertPositionIndex = 0;
        parseNextPosition();
    }

    private void parseNextPosition() {
        if (insertPositionIndex >= selected.size()) {
            removeAllSelectionHandlers();
            refreshWarehouse(activeWarehouse, true);
            getClientController().pickResources(marbleSelection, positions, conversions);
            SceneController.endMainAction();
            return;
        }
        Marble marble = selected.get(insertPositionIndex);
        this.marbleToInsert = marble;
        if (marble.getResourceType().equals(ResourceType.FAITH)) {
            insertPositionIndex++;
            parseNextPosition();
            return;
        }
        if (marble.getResourceType().equals(ResourceType.BLANK) && changeEffects.isEmpty()) {
            insertPositionIndex++;
            parseNextPosition();
            return;
        }
        if (marble.getResourceType().equals(ResourceType.BLANK) && changeEffects.size() == 1) {
            marble = new Marble(conversions.get(0));
        }
        if(marble.getResourceType().equals(ResourceType.BLANK) && changeEffects.size() == 2){

        }
        if (!marble.getResourceType().equals(ResourceType.BLANK) ||
                marble.getResourceType().equals(ResourceType.BLANK) && !changeEffects.isEmpty()) {
            getClientController().viewInfoMessage("Insert position in which you want to add "+ marble.getResourceType().toString());
            // TODO manage leader card depots
            askForDestinationDepot(marble);
            // displayPossibleResourcePositions(depotEffects);
        }
    }

    public void askForDestinationDepot(Marble marble) {
        this.marbleToInsert = marble;
        this.setSwapMenuEnable(false);
        dropResourceButton.setVisible(true);
        if (activeWarehouse.isInitialized()) {
            // iterate through depots
            for (int i = 0; i < 3; i++) {
                ResourceDepot tempDepot = activeWarehouse.getResourceDepot(i);
                // iterate through positions in depots
                for (int j = 0; j < tempDepot.getCapacity(); j++) {
                    Pane resourcePane = (Pane) warehouseElements.get(i).get(j);
                    if (j < tempDepot.getQuantity()) {
                        // if the position is an actual stored resource set a backround image
                        String iconPath = getPathByResourceType(activeWarehouse.getResourceDepot(i).getResourceType());
                        resourcePane.setStyle("-fx-background-image: url(" + iconPath + ");");
                    } else {
                        // if the position is free set an empty background (no image)
                        int triggeredPaneRowIndex = Integer.parseInt(resourcePane.getId().split("-")[1]);
                        int triggeredPaneColumnIndex = Integer.parseInt(resourcePane.getId().split("-")[2]);
                        if (!occupiedCells.contains(new Pair<Integer, Integer>(triggeredPaneRowIndex, triggeredPaneColumnIndex))) {
                            resourcePane.setStyle("-fx-background-image: url('/img/ico/zero.png');");
                            resourcePane.addEventHandler(MouseEvent.MOUSE_CLICKED, onMarbleDestinationChosen);
                        }
                    }
                }
            }
        } else {
            // warehouse is not initialized yet
            for (ObservableList<Node> nodeList : warehouseElements) {
                for (Node node : nodeList) {
                    Pane resourcePane = ((Pane) node);
                    int triggeredPaneRowIndex = Integer.parseInt(resourcePane.getId().split("-")[1]);
                    int triggeredPaneColumnIndex = Integer.parseInt(resourcePane.getId().split("-")[2]);
                    if (!occupiedCells.contains(new Pair<Integer, Integer>(triggeredPaneRowIndex, triggeredPaneColumnIndex))) {
                        resourcePane.setStyle("-fx-background-image: url('/img/ico/zero.png');");
                        resourcePane.addEventHandler(MouseEvent.MOUSE_CLICKED, onMarbleDestinationChosen);
                    }
                }
            }
        }
        dropResourceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onDropResourceClicked );
    }

    private final EventHandler<MouseEvent> onDropResourceClicked = event -> {
        positions.add(ResourcePosition.DROPPED);
        insertPositionIndex++;
        parseNextPosition();
    };

    private final EventHandler<MouseEvent> onClickedResourceToDrop = event -> {
        if(resourcePositionToDrop == null) {
            Pane triggeredPane = (Pane) event.getSource();
            triggeredPane.setEffect(new Glow(0.6));
            int triggeredPaneRowIndex = Integer.parseInt(triggeredPane.getId().split("-")[1]);
            switch (triggeredPaneRowIndex) {
                case 1 -> resourcePositionToDrop = ResourcePosition.FIRST_DEPOT;
                case 2 -> resourcePositionToDrop = ResourcePosition.SECOND_DEPOT;
                case 3 -> resourcePositionToDrop = ResourcePosition.THIRD_DEPOT;
            }
            addResourceRemovalHandlerToDropButton();
            addUnclickedResourceHandler(triggeredPane);
            dropResourceButton.setVisible(true);
        }
    };

    private void addUnclickedResourceHandler(Pane pane){
        pane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResourceToDrop);
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, onUnclickedResourceToDrop);
    }

    private final EventHandler<MouseEvent> onUnclickedResourceToDrop = event -> {
        Pane triggeredPane = (Pane) event.getSource();
        triggeredPane.setEffect(null);
        resourcePositionToDrop = null;
        dropResourceButton.setVisible(false);
    };

    public void addResourceRemovalHandlerToDropButton(){
        dropResourceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onRemoveResourceConfirmClicked);
    }

    private final EventHandler<MouseEvent> onRemoveResourceConfirmClicked = event -> {
        dropResourceButton.setVisible(false);
        getClientController().removeResource(resourcePositionToDrop);
        this.resourcePositionToDrop = null;
    };

    private final EventHandler<MouseEvent> onMarbleDestinationChosen = event -> {
        Pane triggeredPane = (Pane) event.getSource();
        String iconPath = this.getPathByResourceType(marbleToInsert.getResourceType());
        triggeredPane.setStyle("-fx-background-image: url(" + iconPath + ");");
        triggeredPane.setEffect(new Glow(0.6));
        int triggeredPaneRowIndex = Integer.parseInt(triggeredPane.getId().split("-")[1]);
        int triggeredPaneColumnIndex = Integer.parseInt(triggeredPane.getId().split("-")[2]);

        this.occupiedCells.add(new Pair<>(triggeredPaneRowIndex, triggeredPaneColumnIndex));
        switch (triggeredPaneRowIndex) {
            case 1 -> positions.add(ResourcePosition.FIRST_DEPOT);
            case 2 -> positions.add(ResourcePosition.SECOND_DEPOT);
            case 3 -> positions.add(ResourcePosition.THIRD_DEPOT);
        }
        removeMarbleDestinationChosenHandler(triggeredPane);
        insertPositionIndex++;
        parseNextPosition();
    };

    public void removeMarbleDestinationChosenHandler(Pane pane) {
        pane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onMarbleDestinationChosen);
    }

    public void removeAllSelectionHandlers() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <i+1; j++) {
                Pane resourcePane = (Pane) warehouseElements.get(i).get(j);
                resourcePane.setStyle("-fx-background-image: url('/img/ico/zero.png');");
                resourcePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onMarbleDestinationChosen);
                resourcePane.setEffect(null);
            }
        }

        dropResourceButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, onDropResourceClicked);
        dropResourceButton.setVisible(false);
    }


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

    public void setResourcesAsPickable(boolean isPickable){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <i+1; j++) {
                Pane resourcePane = (Pane) warehouseElements.get(i).get(j);
                if(j < activeWarehouse.getResourceDepot(i).getQuantity() && isPickable) {
                    resourcePane.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
                }else{
                    resourcePane.setEffect(null);
                    resourcePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
                }
            }
        }
    }

    private final EventHandler<MouseEvent> onClickedResource = event -> {
        Pane triggeredPane = (Pane) event.getSource();
        int rowIndex = Integer.parseInt(triggeredPane.getId().split("-")[1]);
        triggeredPane.setEffect(new Glow(0.6));
        removePickHandler(triggeredPane);
        ResourcePosition resourcePosition = null;
        switch (rowIndex){
            case 1 -> resourcePosition = ResourcePosition.FIRST_DEPOT;
            case 2 -> resourcePosition = ResourcePosition.SECOND_DEPOT;
            case 3 -> resourcePosition = ResourcePosition.THIRD_DEPOT;

        }
        SceneController.getMainGUIController().getPickResourcesFromStorageController().addFromWarehouse(resourcePosition, activeWarehouse.getResourceDepot(rowIndex-1).getResourceType());
    };

    public void removePickHandler(Pane pane){
        pane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
    }

}
