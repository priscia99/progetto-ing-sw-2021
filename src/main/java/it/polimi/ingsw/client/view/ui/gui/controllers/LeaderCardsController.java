package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.awt.*;
import java.io.File;
import java.util.Objects;

public class LeaderCardsController {
    private static final String LEADER_CARD_FRONT_PATH = "/img/cards/front/leader-card-";
    private static final String LEADER_CARD_BACK_PATH = "/img/cards/back/lead_back.png";
    GridPane leaderCardsPane;
    ObservableList<Node> leaderCardsList;

    public LeaderCardsController(GridPane leaderCardsPane) {
        this.leaderCardsPane = leaderCardsPane;
        leaderCardsList = leaderCardsPane.getChildren();
    }

    /**
     * Refreshes leader cards pane view in main GUI given the player's deck
     * @param leaderCardsDeck player's leader cards deck
     */
    public void refreshLeaderCards(ClientLeaderCardDeck leaderCardsDeck){
        // if deck's size is greater than 2 -> user still has to choose initial leader cards
        if(leaderCardsDeck.getClientLeaderCards().size() > 2){return;}
        for(int i=0; i<leaderCardsDeck.getClientLeaderCards().size(); i++) {
            ClientLeaderCard tempCard = leaderCardsDeck.getClientLeaderCards().get(i);
            String cardImagePath = LEADER_CARD_FRONT_PATH + tempCard.getAssetLink() + ".png";
            Pane cardPane = (Pane)(leaderCardsList.get(i));
            cardPane.setStyle("-fx-background-image: url(" + cardImagePath + ");");
        }
    }

}
