package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class EndTurnButtonController extends GenericGUIController{
    private Button endTurnButton;

    public EndTurnButtonController(ClientController clientController, Button endTurnButton) {
        super(clientController);
        this.endTurnButton = endTurnButton;
        this.endTurnButton.setDisable(true);
        this.endTurnButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onEndButtonClicked);
    }

    public void enableEndTurn(boolean enable){
        this.endTurnButton.setDisable(!enable);
    }

    private final EventHandler<MouseEvent> onEndButtonClicked = event -> {
        getClientController().endTurn();
    };


}
