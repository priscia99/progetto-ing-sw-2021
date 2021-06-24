package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class ChooseLeadersController {

    private static final String LEADER_CARD_FRONT_PATH = "/img/cards/front/leader-card-";
    private static final String LEADER_CARD_BACK_PATH = "/img/cards/back/lead_back.png";

    private AnchorPane chooseLeadersPane;
    private GridPane chooseLeadersGrid;
    private ObservableList<Node> cardsPanes;
    private Button confirmLeadersButton;

    ClientController currentController;
    ArrayList<String> selectedIDs = null;
    ArrayList<String> cardIDs = null;

    public ChooseLeadersController(AnchorPane chooseLeadersPane, Button confirmLeadersButton) {
        this.chooseLeadersPane = chooseLeadersPane;
        this.chooseLeadersGrid = (GridPane)chooseLeadersPane.getChildren().get(1);
        this.cardsPanes = chooseLeadersGrid.getChildren();
        this.confirmLeadersButton = confirmLeadersButton;
    }

    public void activeScreen(ArrayList<String> cardIDs) {
        this.cardIDs = cardIDs;
        this.selectedIDs = new ArrayList<>();
        this.refreshAvailableCards();
        chooseLeadersPane.setVisible(true);
        confirmLeadersButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onConfirmLeadersBtnClicked);
    }

    public void setCurrentController(ClientController controller){
        this.currentController = controller;
    }

    public void refreshAvailableCards(){
        // FIXME ricevo uno 0 come primo id della carta leader!
        for(int i=0; i<4; i++){
            Pane cardPane = (Pane) cardsPanes.get(i);
            String filePath = LEADER_CARD_FRONT_PATH + cardIDs.get(i).substring(1) + ".png";
            System.out.println("Leader path: " + LEADER_CARD_FRONT_PATH + cardIDs.get(i).substring(1) + ".png");
            cardPane.setStyle("-fx-background-image: url(" + filePath + ");");
            cardPane.addEventHandler(MouseEvent.MOUSE_CLICKED, onLeaderCardClicked);
        }
        chooseLeadersPane.requestLayout();
    }

    EventHandler<MouseEvent> onConfirmLeadersBtnClicked = event -> {
        if(selectedIDs.size() == 2) {
            currentController.chooseInitialLeaders(selectedIDs);
            chooseLeadersPane.setVisible(false);
        }
    };

    EventHandler<MouseEvent> onLeaderCardClicked = event -> {
        Pane clickedPane = (Pane) event.getSource();
        int cardIndex = Integer.parseInt(clickedPane.getId()) - 1;
        if(selectedIDs.contains(cardIDs.get(cardIndex))){
            selectedIDs.remove(cardIDs.get(cardIndex));
            clickedPane.setEffect(null);
        }
        else if(selectedIDs.size()<2){
            selectedIDs.add(cardIDs.get(cardIndex));
            clickedPane.setEffect(new Glow(0.6));
        }
    };
}