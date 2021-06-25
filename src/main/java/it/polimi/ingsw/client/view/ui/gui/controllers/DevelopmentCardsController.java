package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientDevelopmentCardDecks;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class DevelopmentCardsController extends GenericGUIController {

    private static final String DEV_CARDS_FRONT_PATH = "/img/cards/front/development-card-";
    private static final String DEV_CARDS_BACK_PATH = "/img/cards/back/development-card-";

    private final ArrayList<ObservableList<Node>> cardsSlots;
    private final Button produceButton;

    public DevelopmentCardsController(ClientController clientController, AnchorPane firstDevSlot, AnchorPane secondDevSlot, AnchorPane thirdDevSlot, Button produceButton) {
        super(clientController);
        this.produceButton = produceButton;
        cardsSlots = new ArrayList<>();
        cardsSlots.add(firstDevSlot.getChildren());
        cardsSlots.add(secondDevSlot.getChildren());
        cardsSlots.add(thirdDevSlot.getChildren());
    }

    /**
     * Refreshes development cards pane based on player's decks
     * @param decks Player's development card decks
     */
    public void refreshDevelopmentCards(ClientDevelopmentCardDecks decks, boolean isMine){
        produceButton.setDisable(!isMine);
        if(decks.isInitialized()) {
            Pane tempPane;  // temporary card pane
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    tempPane = (Pane) cardsSlots.get(i).get(j);
                    if (j < decks.getDeck(i).size()) {
                        // current slot position is occupied by a card (set front background image)
                        String cardImagePath = DEV_CARDS_FRONT_PATH + decks.getDeck(i).get(j).getAssetLink() + ".png";
                        tempPane.setStyle("-fx-background-image: url(" + cardImagePath + ");");
                    } else {
                        // current slot position is free (set empty background image)
                        tempPane.setStyle("-fx-background-image: none;");
                    }
                }
            }
        }
    }

    public void setProduceButtonEnable(boolean isEnable) {
        produceButton.setDisable(!isEnable);
    }
}
