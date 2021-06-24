package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Objects;

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
        for(int i=0; i<4; i++){
            Pane cardPane = (Pane) cardsPanes.get(i);
            Image cardImage = new Image(Objects.requireNonNull(SceneController.class.getResourceAsStream(
                    LEADER_CARD_FRONT_PATH + cardIDs.get(i).substring(1) + ".png")));
            System.out.println(LEADER_CARD_FRONT_PATH + cardIDs.get(i).substring(1) + ".png");
            BackgroundImage bgImg = new BackgroundImage(cardImage,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
            cardPane.setBackground(new Background(bgImg));  // setting marble image backround
            cardPane.addEventHandler(MouseEvent.MOUSE_CLICKED, onLeaderCardClicked);
        }
    }

    EventHandler<MouseEvent> onConfirmLeadersBtnClicked = event -> {
        currentController.chooseInitialLeaders(selectedIDs);
        chooseLeadersPane.setVisible(false);
    };

    EventHandler<MouseEvent> onLeaderCardClicked = event -> {
        Pane clickedPane = (Pane) event.getSource();
        int cardIndex = Integer.parseInt(clickedPane.getId()) - 1;
        if(selectedIDs.contains(cardIDs.get(cardIndex))){
            selectedIDs.remove(cardIDs.get(cardIndex));
        }
        else{
            selectedIDs.add(cardIDs.get(cardIndex));
        }
    };
}
