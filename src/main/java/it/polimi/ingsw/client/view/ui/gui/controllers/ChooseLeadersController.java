package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.view.ui.gui.utils.AssetsHelper;
import it.polimi.ingsw.client.view.ui.gui.utils.FXHelper;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class ChooseLeadersController extends GenericGUIController{

    private AnchorPane chooseLeadersPane;
    private final ObservableList<Node> cardsPanes;
    private final Button confirmLeadersButton;

    ArrayList<String> selectedIDs = null;
    ArrayList<String> cardIDs = null;

    public ChooseLeadersController(ClientController clientController, AnchorPane chooseLeadersPane, Button confirmLeadersButton) {
        super(clientController);
        this.chooseLeadersPane = chooseLeadersPane;
        GridPane chooseLeadersGrid = (GridPane) chooseLeadersPane.getChildren().get(1);
        this.cardsPanes = chooseLeadersGrid.getChildren();
        this.confirmLeadersButton = confirmLeadersButton;
    }

    /**
     * Activates the choose leader cards screen
     * @param cardIDs
     */
    public void activeScreen(ArrayList<String> cardIDs) {
        this.cardIDs = cardIDs;
        this.selectedIDs = new ArrayList<>();
        this.refreshAvailableCards();
        chooseLeadersPane.setVisible(true);
        confirmLeadersButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onConfirmLeadersBtnClicked);
    }

    /**
     *  Refreshes all available leader cards and add handlers on them
     */
    public void refreshAvailableCards(){
        for(int i=0; i<4; i++){
            Pane cardPane = (Pane) cardsPanes.get(i);
            FXHelper.setBackground(cardPane, AssetsHelper.getLeaderFrontPath(cardIDs.get(i).substring(1)));
            cardPane.addEventHandler(MouseEvent.MOUSE_CLICKED, onLeaderCardClicked);
        }
        chooseLeadersPane.requestLayout();
    }

    /**
     * Handler that is triggered when user confirm his leader cards choice
     */
    private EventHandler<MouseEvent> onConfirmLeadersBtnClicked = event -> {
        if(selectedIDs.size() == 2) {
            super.getClientController().chooseInitialLeaders(selectedIDs);
            chooseLeadersPane.setVisible(false);
        }
    };

    /**
     * Handler that is triggered when user choose a leader card
     */
    private EventHandler<MouseEvent> onLeaderCardClicked = event -> {
        Pane clickedPane = (Pane) event.getSource();
        int cardIndex = Integer.parseInt(clickedPane.getId()) - 1;
        if(selectedIDs.contains(cardIDs.get(cardIndex))){
            selectedIDs.remove(cardIDs.get(cardIndex));
            FXHelper.cleanEffects(clickedPane);

        }
        else if(selectedIDs.size()<2){
            selectedIDs.add(cardIDs.get(cardIndex));
            FXHelper.highlight(clickedPane);
        }
    };
}
