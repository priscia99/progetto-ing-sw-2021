package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientCard;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaderCardsController extends GenericGUIController{
    private static final String LEADER_CARD_FRONT_PATH = "/img/cards/front/leader-card-";
    private static final String LEADER_CARD_BACK_PATH = "/img/cards/back/lead_back.png";

    private GridPane leaderCardsPane;
    private ObservableList<Node> leaderCardsPanesList;
    private AnchorPane leaderZoomPane;
    private GridPane leaderCardZoomGrid;
    private Pane leaderCardZoomImage;
    private Map<Pane, ClientLeaderCard> mappedCards;
    private boolean canUserDoAction;
    private ClientCard selectedCard;

    public LeaderCardsController(ClientController clientController, GridPane leaderCardsPane, AnchorPane leaderZoomPane, GridPane leaderCardZoomGrid, Pane leaderCardZoomImage) {
        super(clientController);
        this.canUserDoAction = false;
        this.leaderCardsPane = leaderCardsPane;
        this.leaderZoomPane = leaderZoomPane;
        this.leaderCardZoomGrid = leaderCardZoomGrid;
        this.leaderCardZoomImage = leaderCardZoomImage;
        leaderCardsPanesList = leaderCardsPane.getChildren();
    }

    /**
     * Refreshes leader cards pane view in main GUI given the player's deck
     * @param leaderCardsDeck player's leader cards deck
     */
    public void refreshLeaderCards(ClientLeaderCardDeck leaderCardsDeck, boolean isMine) {
        mappedCards = new HashMap<>();
        // if deck's size is greater than 2 -> user still has to choose initial leader cards
        if (leaderCardsDeck.getClientLeaderCards().size() > 2) {
            return;
        }
        for (int i = 0; i < leaderCardsDeck.getClientLeaderCards().size(); i++) {
            ClientLeaderCard tempCard = leaderCardsDeck.getClientLeaderCards().get(i);
            String cardImagePath = LEADER_CARD_FRONT_PATH + tempCard.getAssetLink() + ".png";
            Pane cardPane = (Pane) (leaderCardsPanesList.get(i));
            mappedCards.put(cardPane, tempCard);
            if (isMine || tempCard.isActive())
                cardPane.setStyle("-fx-background-image: url(" + cardImagePath + ");");
            else
                cardPane.setStyle("-fx-background-image: url(" + LEADER_CARD_BACK_PATH + ");");
        }
        if(isMine && canUserDoAction){
            enableLeaderCardsHandlers();
        }else{
            disableLeaderCardsHandlers();
        }
    }

    public void disableLeaderCardsHandlers() {
        // disable leader cards handlers
        for(Pane cardPane: new ArrayList<>(mappedCards.keySet())){
            cardPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedLeaderCard);
        }
    }

    public void enableLeaderCardsHandlers(){
        // enable leader cards handlers
        for(Pane cardPane: new ArrayList<>(mappedCards.keySet())){
            cardPane.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedLeaderCard);
        }
    }

    public void setCanUserDoAction(boolean canUserDoAction){
        this.canUserDoAction = canUserDoAction;
    }

    private final EventHandler<MouseEvent> onClickedLeaderCard = event -> {
        showCardZoom(mappedCards.get((Pane)event.getSource()));
    };

    public void showCardZoom(ClientLeaderCard clientLeaderCard){
        this.selectedCard = clientLeaderCard;
        String cardAssetPath = clientLeaderCard.getAssetLink();
        Button playLeaderCardButton = (Button)((AnchorPane)leaderCardZoomGrid.getChildren().get(1)).getChildren().get(0);
        Button closeZoomButton = (Button)((AnchorPane)leaderCardZoomGrid.getChildren().get(2)).getChildren().get(0);

        if(clientLeaderCard.isActive()) {
            playLeaderCardButton.setDisable(true);
        }else {
            playLeaderCardButton.setDisable(false);
            playLeaderCardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onPlayCardButtonPressed);
        }
        closeZoomButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onCloseZoomButtonPressed);

        String cardImagePath = LEADER_CARD_FRONT_PATH + cardAssetPath + ".png";
        leaderCardZoomImage.setStyle("-fx-background-image: url(" + cardImagePath + ");");
        leaderZoomPane.setVisible(true);
    }

    private final EventHandler<MouseEvent> onCloseZoomButtonPressed = event -> {
        leaderZoomPane.setVisible(false);
    };

    private final EventHandler<MouseEvent> onPlayCardButtonPressed = event -> {
        super.getClientController().activateLeaderCard(this.selectedCard.getId());
        leaderZoomPane.setVisible(false);
    };
}
