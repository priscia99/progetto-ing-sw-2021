package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientCard;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.client.view.ui.gui.utils.AssetsHelper;
import it.polimi.ingsw.client.view.ui.gui.utils.FXHelper;
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
    private int activeDepotsNumber;

    public LeaderCardsController(ClientController clientController, GridPane leaderCardsPane, AnchorPane leaderZoomPane, GridPane leaderCardZoomGrid, Pane leaderCardZoomImage,
                                 Pane leader1depot1, Pane leader1depot2, Pane leader2depot1, Pane leader2depot2) {
        super(clientController);
        this.canUserDoAction = false;
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
            FXHelper.setBackground(leaderCardsPanesList.get(i), AssetsHelper.getBackLeaderPath());
        }
        for (int i = 0; i < leaderCardsDeck.getClientLeaderCards().size(); i++) {
            ClientLeaderCard tempCard = leaderCardsDeck.getClientLeaderCards().get(i);
            Pane cardPane = (Pane) (leaderCardsPanesList.get(i));
            mappedCards.put(cardPane, tempCard);
            if (isMine || tempCard.isActive()) {
                FXHelper.setBackground(cardPane, AssetsHelper.getLeaderFrontPath(tempCard));
            }
            else
                FXHelper.setBackground(cardPane, AssetsHelper.getBackLeaderPath());
            if(tempCard.isActive()){
                FXHelper.highlight(cardPane);
            } else {
                FXHelper.cleanEffects(cardPane);
            }
        }
        if(isMine && canUserDoAction){
            enableLeaderCardsHandlers();
        }else{
            disableLeaderCardsHandlers();
        }
        setResourcesAsPickable(false);
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

        FXHelper.setBackground(leaderCardZoomImage, AssetsHelper.getLeaderFrontPath(clientLeaderCard));
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
        if(isPickable){
            disableLeaderCardsHandlers();
        }
        this.depotResourceTypes = new ArrayList<>();
        this.leaderDepotActivePanes = new HashMap<>();
        this.activeDepotsNumber = 0;
        FXHelper.cleanBackground(leader1depot1);
        FXHelper.cleanBackground(leader1depot2);
        FXHelper.cleanBackground(leader2depot1);
        FXHelper.cleanBackground(leader2depot2);
        FXHelper.cleanEffects(leader1depot1);
        FXHelper.cleanEffects(leader1depot2);
        FXHelper.cleanEffects(leader2depot1);
        FXHelper.cleanEffects(leader2depot2);
        for(int i=0; i<leaderCardsDeck.getClientLeaderCards().size(); i++){
            ClientLeaderCard tempCard = leaderCardsDeck.getCard(i);
            if(tempCard.getEffect().getEffectType().equals(EffectType.DEPOT)){
                this.activeDepotsNumber++;
                DepotEffect depotEffect = (DepotEffect) tempCard.getEffect();
                ResourceDepot leaderDepot = depotEffect.getDepot();
                ResourceType depotType = leaderDepot.getResourceType();
                depotResourceTypes.add(depotType);
                if(depotType != null && depotType != ResourceType.BLANK && tempCard.isActive()){
                    if(i==0){
                        leaderDepotActivePanes.put("leader-1-depot-1", leader1depot1);
                        leaderDepotActivePanes.put("leader-1-depot-2", leader1depot2);
                    }else if(i==1){
                        leaderDepotActivePanes.put("leader-2-depot-1", leader2depot1);
                        leaderDepotActivePanes.put("leader-2-depot-2", leader2depot2);
                    }
                    if(leaderDepot.getQuantity() > 0){
                        if(i==0){
                            if(isPickable){
                                leader1depot1.addEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            }else{
                                leader1depot1.removeEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            }
                            FXHelper.setBackground(leader1depot1, AssetsHelper.getResourceIconPath(depotType));
                            leaderDepotActivePanes.remove("leader-1-depot-1");
                        }else{
                            if(isPickable){
                                leader2depot1.addEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            } else{
                                leader2depot1.removeEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            }
                            FXHelper.setBackground(leader2depot1,AssetsHelper.getResourceIconPath(depotType) );
                            leaderDepotActivePanes.remove("leader-2-depot-1");
                        }
                    }
                    if(leaderDepot.getQuantity() > 1){
                        if(i==0){
                            if(isPickable){
                                leader1depot2.addEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            } else{
                                leader1depot2.removeEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            }
                            FXHelper.setBackground(leader1depot2, AssetsHelper.getResourceIconPath(depotType));
                            leaderDepotActivePanes.remove("leader-1-depot-2");
                        }else{
                            if(isPickable){
                                leader2depot2.addEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            } else {
                                leader2depot2.removeEventHandler(MouseEvent.MOUSE_CLICKED, onPickedDepotResource);
                            }
                            FXHelper.setBackground(leader2depot2,  AssetsHelper.getResourceIconPath(depotType));
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
        }else{
            ResourceType selectedResType = ((DepotEffect)(leaderCardsDeck.getCard(1).getEffect())).getResourceType();
            if(activeDepotsNumber == 1){
                SceneController.getMainGUIController()
                        .getPickResourcesFromStorageController().addFromLeaderDepot(ResourcePosition.FIRST_LEADER_DEPOT,selectedResType);
            }
            else{
                SceneController.getMainGUIController()
                        .getPickResourcesFromStorageController().addFromLeaderDepot(ResourcePosition.SECOND_LEADER_DEPOT,selectedResType);
            }
        }
        FXHelper.highlight(triggeredPane);
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

    public int getActiveDepotsNumber(){
        return activeDepotsNumber;
    }
}
