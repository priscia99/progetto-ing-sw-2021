package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.server.model.resource.ConsumeTarget;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.Map;

public class ChooseResourcesController extends GenericGUIController{

    Map<String, Pane> chooseResourcesIcons;
    Map<String, Button> chooseResourcesButtons;
    AnchorPane chooseResourcesPane;
    Label chooseResourcesLabel;

    ConsumeTarget chosenResources;
    ResourceType tempChosenType = null;
    int resourcesToChoose = 0;

    public ChooseResourcesController(ClientController clientController, AnchorPane chooseResourcesPane, Label chooseResourcesLabel, Map<String, Pane> chooseResourcesIcons, Map<String, Button> chooseResourcesButtons)  {
        super(clientController);
        this.chooseResourcesPane = chooseResourcesPane;
        this.chooseResourcesLabel = chooseResourcesLabel;
        this.chooseResourcesIcons = chooseResourcesIcons;
        this.chooseResourcesButtons = chooseResourcesButtons;
    }

    /**
     * Activates the choose resources screen and setup all listeners
     * @param toChoose number of resources to choose
     */
    public void activeScreen(int toChoose){
        // Init parameters
        this.resourcesToChoose = toChoose;
        chosenResources = new ConsumeTarget();

        // Setting proper title test based on number of resources to choose
        if(toChoose == 1)
            chooseResourcesLabel.setText("CHOOSE " + toChoose + " RESOURCE");
        else
            chooseResourcesLabel.setText("CHOOSE " + toChoose + " RESOURCES");

        // Setting up all listeners
        this.addIconsHandlers();
        this.disableAllButtons();

        chooseResourcesPane.setVisible(true);
    }

    /**
     * Handler that is triggered when a specific resource is chosen
     * This handler removes all active handlers for resource icons and enable all possible destination buttons
     */
    EventHandler<MouseEvent> onResourceChosen = event -> {
        Pane chosenIconPane = ((Pane)event.getSource());
        chosenIconPane.setEffect(new Glow(0.6));
        switch (chosenIconPane.getId()) {
            case "coin-icon" -> tempChosenType = ResourceType.COIN;
            case "shield-icon" -> tempChosenType = ResourceType.SHIELD;
            case "servant-icon" -> tempChosenType = ResourceType.SERVANT;
            case "stone-icon" -> tempChosenType = ResourceType.STONE;
        }
        this.removeIconsHandlers();
        this.setUnselectedHandler(chosenIconPane);
        this.enableAllButtons();
    };

    /**
     * Handler that is triggered when a specific resource is unselected
     * This handler re-add all handlers to icons and disable all effects for the previous chosen resource
     */
    EventHandler<MouseEvent> onResourceUnselected = event -> {
        tempChosenType = null;
        Pane chosenIconPane = ((Pane)event.getSource());
        chosenIconPane.setEffect(null);
        this.removeIconsHandlers();
        this.addIconsHandlers();
    };

    /**
     * Handler that is triggered when a specific warehouse position is selected
     * This method saves the requested position
     */
    EventHandler<MouseEvent> onSelectedPosition = event -> {
        if(tempChosenType != null) {
            Button clickedButton = (Button) event.getSource();
            ResourcePosition resourcePosition = null;
            switch (clickedButton.getId()) {
                case "firstdepot-button" -> resourcePosition = ResourcePosition.FIRST_DEPOT;
                case "seconddepot-button" -> resourcePosition = ResourcePosition.SECOND_DEPOT;
                case "thirddepot-button" -> resourcePosition = ResourcePosition.THIRD_DEPOT;
            }
            try {
                assert resourcePosition != null;
                chosenResources.put(resourcePosition, new ResourceStock(tempChosenType, 1));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        this.nextChoice();
    };

    /**
     * Decrements the number of resources that were not chosen yet and if all resources are chosen, then sends data to client controller
     * This method also makes all buttons disabled, removes all effects and re-add icon handlers
     */
    private void nextChoice(){
        resourcesToChoose--;
        if(resourcesToChoose == 0){
            chooseResourcesPane.setVisible(false);
            super.getClientController().chooseInitialResources(chosenResources);
        }else{
            this.resetEffects();
            this.removeIconsHandlers();
            this.addIconsHandlers();
            this.disableAllButtons();
        }
    }

    /**
     * Add an handler to the selected resource making that resource unselected if pressed again
     * @param pane
     */
    private void setUnselectedHandler(Pane pane){
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, onResourceUnselected);
    }

    /**
     * Resets all effects in resources icons
     */
    private void resetEffects(){
        chooseResourcesIcons.get("coin-icon").setEffect(null);
        chooseResourcesIcons.get("shield-icon").setEffect(null);
        chooseResourcesIcons.get("stone-icon").setEffect(null);
        chooseResourcesIcons.get("servant-icon").setEffect(null);
    }

    /**
     * Enables all warehouse destination buttons and adds handlers to them
     */
    private void enableAllButtons(){
        chooseResourcesButtons.get("firstdepot-button").setDisable(false);
        chooseResourcesButtons.get("seconddepot-button").setDisable(false);
        chooseResourcesButtons.get("thirddepot-button").setDisable(false);
        chooseResourcesButtons.get("confirmresources-button").setDisable(false);
        chooseResourcesButtons.get("firstdepot-button").addEventHandler(MouseEvent.MOUSE_CLICKED, onSelectedPosition);
        chooseResourcesButtons.get("seconddepot-button").addEventHandler(MouseEvent.MOUSE_CLICKED, onSelectedPosition);
        chooseResourcesButtons.get("thirddepot-button").addEventHandler(MouseEvent.MOUSE_CLICKED, onSelectedPosition);
        chooseResourcesButtons.get("confirmresources-button").addEventHandler(MouseEvent.MOUSE_CLICKED, onSelectedPosition);
    }

    /**
     * Makes all warehouse destination buttons disabled
     */
    private void disableAllButtons(){
        chooseResourcesButtons.get("firstdepot-button").setDisable(true);
        chooseResourcesButtons.get("seconddepot-button").setDisable(true);
        chooseResourcesButtons.get("thirddepot-button").setDisable(true);
        chooseResourcesButtons.get("confirmresources-button").setDisable(true);
    }

    /**
     * Adds handlers to all resource icons
     */
    private void addIconsHandlers(){
        chooseResourcesIcons.get("coin-icon").addEventHandler(MouseEvent.MOUSE_CLICKED, onResourceChosen);
        chooseResourcesIcons.get("shield-icon").addEventHandler(MouseEvent.MOUSE_CLICKED, onResourceChosen);
        chooseResourcesIcons.get("stone-icon").addEventHandler(MouseEvent.MOUSE_CLICKED, onResourceChosen);
        chooseResourcesIcons.get("servant-icon").addEventHandler(MouseEvent.MOUSE_CLICKED, onResourceChosen);
    }

    /**
     * Removes handlers to all resource icons
     */
    private void removeIconsHandlers(){
        chooseResourcesIcons.get("coin-icon").removeEventHandler(MouseEvent.MOUSE_CLICKED, onResourceChosen);
        chooseResourcesIcons.get("shield-icon").removeEventHandler(MouseEvent.MOUSE_CLICKED, onResourceChosen);
        chooseResourcesIcons.get("stone-icon").removeEventHandler(MouseEvent.MOUSE_CLICKED, onResourceChosen);
        chooseResourcesIcons.get("servant-icon").removeEventHandler(MouseEvent.MOUSE_CLICKED, onResourceChosen);
    }

}
