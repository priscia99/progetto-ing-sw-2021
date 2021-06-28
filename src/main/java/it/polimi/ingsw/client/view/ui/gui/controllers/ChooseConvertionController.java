package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.server.model.resource.ResourceType;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class ChooseConvertionController extends GenericGUIController {

    private AnchorPane chooseConvertionPane;
    private Pane firstResourceButton;
    private Pane secondResourceButton;

    private ResourceType firstChoice;
    private ResourceType secondChoice;

    public ChooseConvertionController(ClientController clientController, AnchorPane chooseConvertionPane, Pane firstChoicePane, Pane secondChoicePane) {
        super(clientController);
        this.firstResourceButton = firstChoicePane;
        this.secondResourceButton = secondChoicePane;
        this.chooseConvertionPane = chooseConvertionPane;
    }

    public void showSelection(ResourceType firstChoice, ResourceType secondChoice) {
        this.firstChoice = firstChoice;
        this.secondChoice = secondChoice;

        chooseConvertionPane.setVisible(true);
        firstResourceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onResourceClicked);
        secondResourceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onResourceClicked);
    }

    private EventHandler<MouseEvent> onResourceClicked = event -> {
        Pane clickedPane = (Pane) event.getSource();
        ResourceType selected;
        if(clickedPane.equals(firstResourceButton)){
            selected = firstChoice;
        } else {
            selected = secondChoice;
        }
        chooseConvertionPane.setVisible(false);
        SceneController.getMainGUIController().getWarehouseController().convertionSelected(selected);
    };
}
