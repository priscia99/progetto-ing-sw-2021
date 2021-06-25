package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.model.ClientDevelopmentCardDecks;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class DevelopmentCardsController {

    private static final String DEV_CARDS_FRONT_PATH = "/img/cards/front/development-card-";
    private static final String DEV_CARDS_BACK_PATH = "/img/cards/back/development-card-";

    private AnchorPane firstDevSlot, secondDevSlot, thirdDevSlot;
    private ArrayList<ObservableList<Node>> cardsSlots;
    private Button produceButton;

    public DevelopmentCardsController(AnchorPane firstDevSlot, AnchorPane secondDevSlot, AnchorPane thirdDevSlot, Button produceButton) {
        this.produceButton = produceButton;
        cardsSlots = new ArrayList<>();
        this.firstDevSlot = firstDevSlot;
        this.secondDevSlot = secondDevSlot;
        this.thirdDevSlot = thirdDevSlot;
        cardsSlots.add(this.firstDevSlot.getChildren());
        cardsSlots.add(this.secondDevSlot.getChildren());
        cardsSlots.add(this.thirdDevSlot.getChildren());
    }

    /**
     * Refreshes development cards pane based on player's decks
     * @param decks Player's development card decks
     */
    public void refreshDevelopmentCards(ClientDevelopmentCardDecks decks, boolean isMine){
        produceButton.setDisable(!isMine);
        if(!decks.isInitialized()) {
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
}
