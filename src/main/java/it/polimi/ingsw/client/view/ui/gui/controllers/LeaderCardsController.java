package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientCard;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.server.model.card.effect.DepotEffect;
import it.polimi.ingsw.server.model.card.effect.EffectType;
import it.polimi.ingsw.server.model.resource.ResourceDepot;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceType;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaderCardsController extends GenericGUIController{
    private static final String COIN_PATH = "/img/ico/coin.png";
    private static final String SERVANT_PATH = "/img/ico/servant.png";
    private static final String SHIELD_PATH = "/img/ico/shield.png";
    private static final String STONE_PATH = "/img/ico/stone.png";
    private static final String LEADER_CARD_FRONT_PATH = "/img/cards/front/leader-card-";
    private static final String LEADER_CARD_BACK_PATH = "/img/cards/back/lead_back.png";

    private final GridPane leaderCardsPane;
    private final ObservableList<Node> leaderCardsPanesList;
    private AnchorPane leaderZoomPane;
    private final GridPane leaderCardZoomGrid;
    private final Pane leaderCardZoomImage;
    private Map<Pane, ClientLeaderCard> mappedCards;
    private ClientLeaderCardDeck leaderCardsDeck;
    private boolean canUserDoAction;
    private ClientCard selectedCard;
    private Pane leader1depot1, leader1depot2, leader2depot1, leader2depot2;
    private Map<String, Pane> leaderDepotActivePanes;
    private ArrayList<ResourceType> depotResourceTypes;

    public LeaderCardsController(ClientController clientController, GridPane leaderCardsPane, AnchorPane leaderZoomPane, GridPane leaderCardZoomGrid, Pane leaderCardZoomImage,
                                 Pane leader1depot1, Pane leader1depot2, Pane leader2depot1, Pane leader2depot2) {
        super(clientController);
        this.canUserDoAction = false;
        this.leaderCardsPane = leaderCardsPane;
        this.leaderZoomPane = leaderZoomPane;
        this.leaderCardZoomGrid = leaderCardZoomGrid;
        this.leaderCardZoomImage = leaderCardZoomImage;
        this.leader1depot1 = leader1depot1;
        this.leader1depot2 = leader1depot2;
        this.leader2depot1 = leader2depot1;
        this.leader2depot2 = leader2depot2;
        leaderCardsPanesList = leaderCardsPane.getChildren();
    }

    /**
     * Refreshes leader cards pane view in main GUI given the player's deck
     * @param leaderCardsDeck player's leader cards deck
     */
    public void refreshLeaderCards(ClientLeaderCardDeck leaderCardsDeck, boolean isMine) {
        this.leaderCardsDeck = leaderCardsDeck;
        mappedCards = new HashMap<>();
        // if deck's size is greater than 2 -> user still has to choose initial leader cards
        if (leaderCardsDeck.getClientLeaderCards().size() > 2) {
            return;
        }
        for(int i = 0; i<2; i++){
            leaderCardsPanesList.get(i).setStyle("-fx-background-image: url(" + LEADER_CARD_BACK_PATH + ");");
        }
        for (int i = 0; i < leaderCardsDeck.getClientLeaderCards().size(); i++) {
            ClientLeaderCard tempCard = leaderCardsDeck.getClientLeaderCards().get(i);
            String cardImagePath = LEADER_CARD_FRONT_PATH + tempCard.getAssetLink() + ".png";
            Pane cardPane = (Pane) (leaderCardsPanesList.get(i));
            mappedCards.put(cardPane, tempCard);
            if (isMine || tempCard.isActive()) {
                cardPane.setStyle("-fx-background-image: url(" + cardImagePath + ");");
            }
            else
                cardPane.setStyle("-fx-background-image: url(" + LEADER_CARD_BACK_PATH + ");");
            if(tempCard.isActive()){
                cardPane.setEffect(new Glow(0.6));
            }
        }
        if(isMine && canUserDoAction){
            enableLeaderCardsHandlers();
        }else{
            disableLeaderCardsHandlers();
        }
        setResourcesAsPickable(false);
    }

    public String getResourcePathByType(ResourceType resourceType){
        String path = null;
        switch (resourceType){
            case COIN -> path = COIN_PATH;
            case SHIELD -> path = SHIELD_PATH;
            case STONE -> path = STONE_PATH;
            case SERVANT -> path = SERVANT_PATH;
        }
        return path;
    }

    public void disableLeaderCardsHandlers() {
        // disable leader cards handlers
        System.out.println("Disabled leader cards handlers");
        for(Pane cardPane: new ArrayList<>(mappedCards.keySet())){
            cardPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedLeaderCard);
        }
    }

    public void enableLeaderCardsHandlers(){
        // enable leader cards handlers
        System.out.println("Enabled leader cards handlers");
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
        Button dropLeaderCardButton = (Button)((AnchorPane)leaderCardZoomGrid.getChildren().get(2)).getChildren().get(0);
        Button closeZoomButton = (Button)((AnchorPane)leaderCardZoomGrid.getChildren().get(3)).getChildren().get(0);
        dropLeaderCardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onDropCardButtonPressed);
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

    private final EventHandler<MouseEvent> onDropCardButtonPressed = event -> {
        super.getClientController().dropLeaderCard(this.selectedCard.getId());
        leaderZoomPane.setVisible(false);
    };

    public void setResourcesAsPickable(boolean isPickable){
        this.depotResourceTypes = new ArrayList<>();
        this.leaderDepotActivePanes = new HashMap<>();
        leader1depot1.setStyle("-fx-background-image: none;");
        leader1depot2.setStyle("-fx-background-image: none;");
        leader2depot1.setStyle("-fx-background-image: none;");
        leader2depot2.setStyle("-fx-background-image: none;");
        leader1depot1.setEffect(null);
        leader1depot2.setEffect(null);
        leader2depot1.setEffect(null);
        leader2depot2.setEffect(null);
        for(int i=0; i<leaderCardsDeck.getClientLeaderCards().size(); i++){
            ClientLeaderCard tempCard = leaderCardsDeck.getCard(i);
            System.out.println("Card effect" + tempCard.getEffect().getEffectType());
            if(tempCard.getEffect().getEffectType().equals(EffectType.DEPOT)){
                DepotEffect depotEffect = (DepotEffect) tempCard.getEffect();
                ResourceDepot leaderDepot = depotEffect.getDepot();
                ResourceType depotType = leaderDepot.getResourceType();
                depotResourceTypes.add(depotType);
                System.out.println("Activated Depot Resource Type: " + depotType);
                if(depotType != null && depotType != ResourceType.BLANK && tempCard.isActive()){
                    System.out.println("Sono entrato");
                    String resourcePath = getResourcePathByType(depotType);
                    if(i==0){
                        leaderDepotActivePanes.put("leader-1-depot-1", leader1depot1);
                        leaderDepotActivePanes.put("leader-1-depot-2", leader1depot2);
                    }else if(i==1){
                        leaderDepotActivePanes.put("leader-2-depot-1", leader2depot1);
                        leaderDepotActivePanes.put("leader-2-depot-2", leader2depot2);
                    }
                    if(leaderDepot.getQuantity() > 0){
                        if(i==0){
                            if(isPickable)
                                leader1depot1.addEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            else
                                leader1depot1.removeEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            leader1depot1.setStyle("-fx-background-image: url(" + resourcePath + ");");
                            leaderDepotActivePanes.remove("leader-1-depot-1");
                        }else{
                            if(isPickable)
                                leader2depot1.addEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            else
                                leader2depot1.removeEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            leader2depot1.setStyle("-fx-background-image: url(" + resourcePath + ");");
                            leaderDepotActivePanes.remove("leader-2-depot-1");
                        }
                    }
                    if(leaderDepot.getQuantity() > 1){
                        if(i==0){
                            if(isPickable)
                                leader1depot2.addEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            else
                                leader1depot2.removeEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            leader1depot2.setStyle("-fx-background-image: url(" + resourcePath + ");");
                            leaderDepotActivePanes.remove("leader-1-depot-2");
                        }else{
                            if(isPickable)
                                leader2depot2.addEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            else
                                leader2depot2.removeEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            leader2depot2.setStyle("-fx-background-image: url(" + resourcePath + ");");
                            leaderDepotActivePanes.remove("leader-2-depot-2");
                        }
                    }
                }
            }
        }
    }

    private final EventHandler<MouseEvent> onPickedDepotResource = event -> {
        Pane triggeredPane = (Pane) event.getSource();
        if(triggeredPane == leader1depot1 || triggeredPane == leader1depot2){
            ResourceType selectedResType = ((DepotEffect)(leaderCardsDeck.getCard(0).getEffect())).getResourceType();
            SceneController.getMainGUIController()
                    .getPickResourcesFromStorageController().addFromLeaderDepot(ResourcePosition.FIRST_LEADER_DEPOT,selectedResType);
        }
        triggeredPane.setEffect(new Glow(0.6));
        removePickedHandlerForPane(triggeredPane);
    };

    private void removePickedHandlerForPane(Pane pane){
        pane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
    }

    public Map<String, Pane> getLeaderDepotActivePanes(){
        return this.leaderDepotActivePanes;
    }

    public ArrayList<ResourceType> getDepotResourceTypes(){
        return this.depotResourceTypes;
    }
}
