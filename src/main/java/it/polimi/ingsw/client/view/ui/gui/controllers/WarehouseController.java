package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.client.model.ClientWarehouse;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.client.view.ui.gui.utils.AssetsHelper;
import it.polimi.ingsw.client.view.ui.gui.utils.FXHelper;
import it.polimi.ingsw.server.model.card.effect.ChangeEffect;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.resource.ResourceDepot;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceType;
import it.polimi.ingsw.utils.Pair;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Map;

public class WarehouseController extends GenericGUIController {

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
    private ArrayList<Pair<Integer, Integer>> occupiedCells;
    private MarbleSelection marbleSelection;
    private ResourcePosition resourcePositionToDrop;
    private Pane resourcePaneToDrop;
    private boolean isManagingResources;
    private Map<String, Pane> leaderDepotActivePanes;

    public WarehouseController(ClientController clientController, GridPane firstDepot, GridPane secondDepot, GridPane thirdDepot, MenuButton swapDepotsMenu, Button dropResourceButton) {
        super(clientController);
        this.isManagingResources = false;
        this.swapDepotsMenu = swapDepotsMenu;
        this.warehouseElements = new ArrayList<>();
        this.resourcePositionToDrop = null;
        this.resourcePositionToDrop = null;
        this.dropResourceButton = dropResourceButton;
        this.warehouseElements.add(firstDepot.getChildren());
        this.warehouseElements.add(secondDepot.getChildren());
        this.warehouseElements.add(thirdDepot.getChildren());
        this.initSwapSelector();
    }

    /**
     * Inits the swap depots selector with all possible choices
     */
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

    /**
     * Handler that is triggered when the user wants depots 1 an 2 to be swapped
     */
    private final EventHandler<ActionEvent> swap1and2 = event -> {
        super.getClientController().swapDepots(0, 1);
    };

    /**
     * Handler that is triggered when the user wants depots 1 an 3 to be swapped
     */
    private final EventHandler<ActionEvent> swap1and3 = event -> {
        super.getClientController().swapDepots(0, 2);
    };

    /**
     * Handler that is triggered when the user wants depots 2 an 3 to be swapped
     */
    private final EventHandler<ActionEvent> swap2and3 = event -> {
        super.getClientController().swapDepots(1, 2);
    };

    /**
     * Refreshes warehouse pane with given warehouse
     *
     * @param warehouse Player's warehouse to show
     */
    public void refreshWarehouse(ClientWarehouse warehouse, boolean isMine) {
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
                        FXHelper.setBackground(resourcePane,AssetsHelper.getResourceIconPath(activeWarehouse.getResourceDepot(i).getResourceType()));
                        if(isMine && !isManagingResources) {
                            resourcePane.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResourceToDrop);
                        }else{
                            resourcePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResourceToDrop);
                        }
                    } else {
                        FXHelper.setBackground(resourcePane, AssetsHelper.getBlackMarblePath());
                        resourcePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResourceToDrop);
                    }
                }
            }
        } else {
            for (ObservableList<Node> nodeList : warehouseElements) {
                for (Node node : nodeList) {
                    FXHelper.setBackground(node, AssetsHelper.getBlackMarblePath());
                }
            }
        }
    }

    /**
     * Sets the swap selector enable based on the parameter given in input
     * @param isEnable set true is the swap selector should be enabled
     */
    public void setSwapMenuEnable(boolean isEnable) {
        swapDepotsMenu.setDisable(!isEnable);
    }

    public void insertResourcesToDepot(MarbleSelection selection, ArrayList<Marble> selected, ArrayList<ClientLeaderCard> changeEffects, ArrayList<ClientLeaderCard> depotEffects) {
        this.leaderDepotActivePanes = SceneController.getMainGUIController().getLeaderCardsController().getLeaderDepotActivePanes();
        this.isManagingResources = true;
        this.marbleSelection = selection;
        this.occupiedCells = new ArrayList<>();
        this.positions = new ArrayList<>();
        this.conversions = new ArrayList<>();
        this.selected = selected;
        this.changeEffects = changeEffects;
        insertPositionIndex = 0;
        parseNextPosition();
    }

    /**
     * Parse the next marble selection when user wants to buy a specific row / column of resources from marble market
     * If all resources were previously selected, tells the controller that the user has finished the action
     */
    private void parseNextPosition() {
        if (insertPositionIndex >= selected.size()) {
            this.isManagingResources = false;
            removeAllSelectionHandlers();
            SceneController.getMainGUIController().getLeaderCardsController().removeLeaderDepotsBackground();
            SceneController.getMainGUIController().getLeaderCardsController().removeLeaderDepotsEffects();
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
            marble = new Marble(((ChangeEffect)changeEffects.get(0).getEffect()).getResourceType());
            this.marbleToInsert = marble;
            conversions.add(marble.getResourceType());
        }
        if(marble.getResourceType().equals(ResourceType.BLANK) && changeEffects.size() == 2){
            ResourceType firstChoice = ((ChangeEffect)changeEffects.get(0).getEffect()).getResourceType();
            ResourceType secondChoice = ((ChangeEffect)changeEffects.get(1).getEffect()).getResourceType();
            SceneController.getMainGUIController().getChooseConversionController().showSelection(firstChoice, secondChoice);
            return;
        }
        if (!marble.getResourceType().equals(ResourceType.BLANK) ||
                marble.getResourceType().equals(ResourceType.BLANK) && !changeEffects.isEmpty()) {
            askForDestinationDepot(marble);
        }
    }

    /**
     * Asks to the player in which depot the requested marble has to be placed
     * @param marble the marble that needs to be placed
     */
    public void askForDestinationDepot(Marble marble) {
        getClientController().viewInfoMessage("Insert position in which you want to add "+ marble.getResourceType().toString());
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
                        FXHelper.setBackground(resourcePane, AssetsHelper.getResourceIconPath(activeWarehouse.getResourceDepot(i).getResourceType()));
                    } else {
                        int triggeredPaneRowIndex = Integer.parseInt(resourcePane.getId().split("-")[1]);
                        int triggeredPaneColumnIndex = Integer.parseInt(resourcePane.getId().split("-")[2]);
                        if (!occupiedCells.contains(new Pair<>(triggeredPaneRowIndex, triggeredPaneColumnIndex))) {
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
                    if (!occupiedCells.contains(new Pair<>(triggeredPaneRowIndex, triggeredPaneColumnIndex))) {
                        resourcePane.addEventHandler(MouseEvent.MOUSE_CLICKED, onMarbleDestinationChosen);
                    }
                }
            }
        }
        SceneController.getMainGUIController().getLeaderCardsController().disableLeaderCardsHandlers();
        this.leaderDepotActivePanes.entrySet().stream().forEach(entry -> entry.getValue().addEventHandler(MouseEvent.MOUSE_CLICKED, onDestinationChosenFromLeaderDepot));
        dropResourceButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, onRemoveResourceConfirmClicked);
        dropResourceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onDropResourceClicked );
    }

    /**
     * Handler that is triggered when the user wants to drop a resource while trying to buy resources from marble market
     */
    private final EventHandler<MouseEvent> onDropResourceClicked = event -> {
        positions.add(ResourcePosition.DROPPED);
        insertPositionIndex++;
        parseNextPosition();
    };

    /**
     * Handler that is triggered when the user selects a resource to be dropped that already present in one of the depots
     */
    private final EventHandler<MouseEvent> onClickedResourceToDrop = event -> {
        if(resourcePositionToDrop == null) {
            Pane triggeredPane = (Pane) event.getSource();
            this.resourcePaneToDrop = triggeredPane;
            FXHelper.highlight(triggeredPane);
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

    /**
     * Adds the handler that manage a resource that is unclicked to a selected pane
     * @param pane pane in which handler has to be added
     */
    private void addUnclickedResourceHandler(Pane pane){
        pane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResourceToDrop);
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, onUnclickedResourceToDrop);
    }

    /**
     * Adds the handler that manage a resource that is clicked to a selected pane
     * @param pane pane in which handler has to be added
     */
    private void addClickedResourceHandler(Pane pane){
        pane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onUnclickedResourceToDrop);
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResourceToDrop);
    }

    /**
     * Handler that is triggered when the player doesn't want anymore to drop a specific resource from a depot
     */
    private final EventHandler<MouseEvent> onUnclickedResourceToDrop = event -> {
        Pane triggeredPane = (Pane) event.getSource();
        FXHelper.cleanEffects(triggeredPane);
        resourcePositionToDrop = null;
        addClickedResourceHandler(triggeredPane);
        dropResourceButton.setVisible(false);
    };

    /**
     * Adds the handler to the Drop Resource button that is triggered when the user wants to drop a resource
     */
    public void addResourceRemovalHandlerToDropButton(){
        dropResourceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onRemoveResourceConfirmClicked);
    }

    /**
     * Handler that is triggered when the player wants to confirm its choice to drop a resource from the depot
     * This handler tells the controller that a resource has to be dropped
     */
    private final EventHandler<MouseEvent> onRemoveResourceConfirmClicked = event -> {
        dropResourceButton.setVisible(false);
        if(this.resourcePaneToDrop != null){
            FXHelper.cleanEffects(resourcePaneToDrop);
            this.resourcePaneToDrop = null;
        }
        getClientController().removeResource(resourcePositionToDrop);
        this.resourcePositionToDrop = null;
    };

    /**
     * Handler that is triggered when a destination for a resource from the marble market has been chosen
     */
    private final EventHandler<MouseEvent> onMarbleDestinationChosen = event -> {
        Pane triggeredPane = (Pane) event.getSource();
        FXHelper.setBackground(triggeredPane, AssetsHelper.getResourceIconPath(marbleToInsert.getResourceType()));
        FXHelper.highlight(triggeredPane);
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

    /**
     * Handler that is triggered when a destination for a resource from the marble market has been chosen and a leader card
     * with depot effects has been selected
     */
    private final EventHandler<MouseEvent> onDestinationChosenFromLeaderDepot = event -> {
        int activeLeaderDepots = SceneController.getMainGUIController().getLeaderCardsController().getActiveDepotsNumber();
        Pane triggeredPane = (Pane) event.getSource();
        FXHelper.setBackground(triggeredPane, AssetsHelper.getResourceIconPath(marbleToInsert.getResourceType()));
        FXHelper.highlight(triggeredPane);

        if(triggeredPane.getId().contains("leader-1")){
            positions.add(ResourcePosition.FIRST_LEADER_DEPOT);
        } else if(triggeredPane.getId().contains("leader-2")){
            if(activeLeaderDepots == 1) {
                positions.add(ResourcePosition.FIRST_LEADER_DEPOT);
            }else {
                positions.add(ResourcePosition.SECOND_LEADER_DEPOT);
            }
        }
        removeMarbleDestinationFromDepotChosenHandler(triggeredPane);
        this.leaderDepotActivePanes.remove(triggeredPane.getId());
        insertPositionIndex++;
        parseNextPosition();
    };

    /**
     * Removes the destination chosen handler for a selected pane
     * @param pane pane which handler needs to be removed
     */
    public void removeMarbleDestinationChosenHandler(Pane pane) {
        pane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onMarbleDestinationChosen);
    }

    /**
     * Removes the destination chosen handler for a selected pane in a leader card with depot effects
     * @param pane pane which handler needs to be removed
     */
    public void removeMarbleDestinationFromDepotChosenHandler(Pane pane) {
        pane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onDestinationChosenFromLeaderDepot);
    }

    /**
     * Removes all selection handlers for resources in the warehouse
     */
    public void removeAllSelectionHandlers() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <i+1; j++) {
                Pane resourcePane = (Pane) warehouseElements.get(i).get(j);
                resourcePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onMarbleDestinationChosen);
                FXHelper.cleanEffects(resourcePane);
            }
        }
        this.leaderDepotActivePanes.entrySet().stream().forEach(entry -> entry.getValue().removeEventHandler(MouseEvent.MOUSE_CLICKED, onDestinationChosenFromLeaderDepot));
        dropResourceButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, onDropResourceClicked);
        dropResourceButton.setVisible(false);
    }

    /**
     * Sets all resources from warehouse as selectable for a buy or production action, based on the parameter given
     * @param isPickable set true is the resources needs to be selectable
     */
    public void setResourcesAsPickable(boolean isPickable){
        isManagingResources = isPickable;
        refreshWarehouse(activeWarehouse, true);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <i+1; j++) {
                Pane resourcePane = (Pane) warehouseElements.get(i).get(j);
                resourcePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onUnclickedResourceToDrop);
                FXHelper.cleanEffects(resourcePane);
                if(activeWarehouse.isInitialized()){
                    if(j < activeWarehouse.getResourceDepot(i).getQuantity() && isPickable) {
                        resourcePane.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
                    }else{
                        resourcePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
                    }
                }
            }
        }
    }

    /**
     * Handler that is triggered when a resource is selected for a buy or production action
     * This handler tells the controller that the specific resource from warehouse has been selected
     */
    private final EventHandler<MouseEvent> onClickedResource = event -> {
        Pane triggeredPane = (Pane) event.getSource();
        int rowIndex = Integer.parseInt(triggeredPane.getId().split("-")[1]);
        FXHelper.highlight(triggeredPane);
        removePickHandler(triggeredPane);
        ResourcePosition resourcePosition = null;
        switch (rowIndex){
            case 1 -> resourcePosition = ResourcePosition.FIRST_DEPOT;
            case 2 -> resourcePosition = ResourcePosition.SECOND_DEPOT;
            case 3 -> resourcePosition = ResourcePosition.THIRD_DEPOT;

        }
        SceneController.getMainGUIController().getPickResourcesFromStorageController().addFromWarehouse(resourcePosition, activeWarehouse.getResourceDepot(rowIndex-1).getResourceType());
    };

    /**
     * Remove the pick resource handler for a specific pane
     * @param pane the pane which handler has to be removed
     */
    public void removePickHandler(Pane pane){
        pane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
    }

    /**
     * Ask for the position in which a white converted marble corresponding resource has to be placed
     * @param selectedResource converted resource type
     */
    public void convertionSelected(ResourceType selectedResource){
        this.conversions.add(selectedResource);
        askForDestinationDepot(new Marble(selectedResource));
    }

}
