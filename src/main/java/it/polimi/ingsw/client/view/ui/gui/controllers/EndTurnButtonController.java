package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
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

    /**
     * Makes the end turn button available
     * @param enable
     */
    public void enableEndTurn(boolean enable){
        this.endTurnButton.setDisable(!enable);
    }

    /**
     * Handler that is triggered when the end turn button is pressed
     * This handler calls the client controller method in order to end the turn
     */
    private final EventHandler<MouseEvent> onEndButtonClicked = event -> {
        SceneController.getMainGUIController().endTurn();
        getClientController().endTurn();
    };


}
