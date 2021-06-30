package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.client.view.ui.gui.utils.AssetsHelper;
import it.polimi.ingsw.client.view.ui.gui.utils.FXHelper;
import it.polimi.ingsw.server.model.resource.ResourceType;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ChooseConversionController extends GenericGUIController {

    private AnchorPane chooseConversionPane;
    private Pane firstResourceButton;
    private Pane secondResourceButton;

    private ResourceType firstChoice;
    private ResourceType secondChoice;

    public ChooseConversionController(ClientController clientController, AnchorPane chooseConversionPane, Pane firstChoicePane, Pane secondChoicePane) {
        super(clientController);
        this.firstResourceButton = firstChoicePane;
        this.secondResourceButton = secondChoicePane;
        this.chooseConversionPane = chooseConversionPane;
    }

    /**
     * Displays the conversion pane to the player who has to choose between two marble conversions
     * @param firstChoice first conversion choice
     * @param secondChoice second conversion choice
     */
    public void showSelection(ResourceType firstChoice, ResourceType secondChoice) {
        this.firstChoice = firstChoice;
        this.secondChoice = secondChoice;
        refreshIcons();
        chooseConversionPane.setVisible(true);
        firstResourceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onResourceClicked);
        secondResourceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onResourceClicked);
    }

    /**
     * Handler which is triggered by a resource which is clicked
     * This handler updates the user chosen conversion marble to the controller
     */
    private final EventHandler<MouseEvent> onResourceClicked = event -> {
        Pane clickedPane = (Pane) event.getSource();
        ResourceType selected = (clickedPane.equals(firstResourceButton)) ? firstChoice : secondChoice;
        chooseConversionPane.setVisible(false);
        SceneController.getMainGUIController().getWarehouseController().convertionSelected(selected);
    };

    /**
     * Refreshes the resource icons in the screen
     */
    private void refreshIcons(){
        FXHelper.setBackground(firstResourceButton, AssetsHelper.getResourceIconPath(firstChoice));
        FXHelper.setBackground(secondResourceButton, AssetsHelper.getResourceIconPath(secondChoice));
    }
}
