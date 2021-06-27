package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientDevelopmentCard;
import it.polimi.ingsw.client.model.ClientDevelopmentCardDecks;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class DevelopmentCardsController extends GenericGUIController {

    private static final String DEV_CARDS_FRONT_PATH = "/img/cards/front/development-card-";
    private static final String DEV_CARDS_BACK_PATH = "/img/cards/back/development-card-";

    private final ArrayList<ObservableList<Node>> cardsSlots;
    private final Button produceButton;
    private ClientDevelopmentCardDecks decks;

    public DevelopmentCardsController(ClientController clientController, AnchorPane firstDevSlot, AnchorPane secondDevSlot, AnchorPane thirdDevSlot, Button produceButton) {
        super(clientController);
        this.produceButton = produceButton;
        cardsSlots = new ArrayList<>();
        cardsSlots.add(firstDevSlot.getChildren());
        cardsSlots.add(secondDevSlot.getChildren());
        cardsSlots.add(thirdDevSlot.getChildren());
        produceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onProduceButtonPressed);
    }

    /**
     * Refreshes development cards pane based on player's decks
     * @param decks Player's development card decks
     */
    public void refreshDevelopmentCards(ClientDevelopmentCardDecks decks, boolean isMine){
        this.decks = decks;
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

    private final EventHandler<javafx.scene.input.MouseEvent> onProduceButtonPressed = event -> {
        ArrayList<ClientDevelopmentCard> availableProductions = new ArrayList<>();
        if(decks != null && decks.isInitialized()){
            for (int i = 0; i < 3; i++) {
                int deckSize = this.decks.getDeck(i).size();
                if (this.decks.getDeck(i).size() > 0) {
                    availableProductions.add(this.decks.getDeck(i).get(deckSize - 1));
                }
            }
        }
        SceneController.getMainGUIController().getProductionController().setAvailableDevelopments(availableProductions);
        super.getClientController().produceCommandHandler();
    };

    public void setProduceButtonEnable(boolean isEnable) {
        produceButton.setDisable(!isEnable);
    }
}
