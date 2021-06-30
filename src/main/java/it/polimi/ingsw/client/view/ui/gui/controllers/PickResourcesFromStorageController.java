package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.resource.ConsumeTarget;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Optional;

public class PickResourcesFromStorageController extends GenericGUIController{

    private Button confirmChooseButton;
    private ConsumeTarget consumeTarget;
    private boolean isProduction;
    private String cardToBuyId;
    private int chosenDeckIndex;
    private ArrayList<String> productionCardsIDs;
    private Optional<ProductionEffect> genericProduction;
    private ArrayList<ProductionEffect> leaderProductions;

    public PickResourcesFromStorageController(ClientController controller, Button confirmChooseButton){
        super(controller);
        this.confirmChooseButton = confirmChooseButton;
        confirmChooseButton.setVisible(false);
        this.productionCardsIDs = new ArrayList<>();
        this.genericProduction = Optional.empty();
        this.leaderProductions = new ArrayList<>();
    }

    /**
     * Enable all resources ready to be selectable in order to buy a development card from the cards market
     * @param cardToBuyId ID of the card that the user wants to buy
     * @param chosenDeckIndex the deck position in which player wants to put the development card
     */
    public void chooseResourcesForBuyAction(String cardToBuyId, int chosenDeckIndex){
        this.isProduction = false;
        this.cardToBuyId = cardToBuyId;
        this.chosenDeckIndex = chosenDeckIndex;

        consumeTarget = new ConsumeTarget();
        confirmChooseButton.setVisible(true);
        confirmChooseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedChooseButton);
        SceneController.getMainGUIController().getWarehouseController().setResourcesAsPickable(true);
        SceneController.getMainGUIController().getStrongBoxController().setResourcesAsPickable(true);
        SceneController.getMainGUIController().getLeaderCardsController().setResourcesAsPickable(true);
    }

    /**
     * Enable all resources ready to be selectable in order to start a production action
     */
    public void chooseResourcesForProduction(){
        this.isProduction = true;
        consumeTarget = new ConsumeTarget();
        confirmChooseButton.setVisible(true);
        confirmChooseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedChooseButton);
        SceneController.getMainGUIController().getWarehouseController().setResourcesAsPickable(true);
        SceneController.getMainGUIController().getStrongBoxController().setResourcesAsPickable(true);
        SceneController.getMainGUIController().getLeaderCardsController().setResourcesAsPickable(true);
    }

    /**
     * Add a resource selected from the strongbox to the list of selectes ones for a production or a buy action
     * @param resourceType the type of selected resource
     */
    public void addFromStrongbox(ResourceType resourceType) {
        try {
            consumeTarget.put(ResourcePosition.STRONG_BOX, new ResourceStock(resourceType, 1));
        }catch (Exception e){
            super.getClientController().viewErrorMessage(e.getMessage());
        }
    }

    /**
     * Add a resource selected from the warehouse to the list of selectes ones for a production or a buy action
     * @param position the depot position of selected resource
     * @param resourceType the type of selected resource
     */
    public void addFromWarehouse(ResourcePosition position, ResourceType resourceType){
        try{
            consumeTarget.put(position, new ResourceStock(resourceType, 1));
        }catch (Exception e){
            super.getClientController().viewErrorMessage(e.getMessage());
        }
    }

    /**
     * Add a resource selected from the an activated leader card with depot effects to the list of selectes ones for a production or a buy action
     * @param position
     * @param resourceType
     */
    public void addFromLeaderDepot(ResourcePosition position, ResourceType resourceType){
        try{
            consumeTarget.put(position, new ResourceStock(resourceType, 1));
        }catch (Exception e){
            super.getClientController().viewErrorMessage(e.getMessage());
        }
    }

    /**
     * Add a card to the list of productions or remove if it was previously selected
     * @param cardID the card ID
     */
    public void toggleProductionID(String cardID){
        if(this.productionCardsIDs.contains(cardID)){
            this.productionCardsIDs.remove(cardID);
        }else {
            this.productionCardsIDs.add(cardID);
        }
    }

    /**
     * Set the generic production
     * @param genericProduction the generic production effect
     */
    public void setGenericProduction(Optional<ProductionEffect> genericProduction){
        this.genericProduction = genericProduction;
    }

    /**
     * Set productions from the leader cards
     * @param leaderProductions a list of productions from leader cards with production effect
     */
    public void setLeaderProductions(ArrayList<ProductionEffect> leaderProductions){
        this.leaderProductions = leaderProductions;
    }

    /**
     * Handler that is triggered when user confirms the resources choise
     * This handler manages to end the action
     */
    private final EventHandler<javafx.scene.input.MouseEvent> onClickedChooseButton = event -> {
        this.disable();
        this.sendPickedResources();
        SceneController.endMainAction();
    };

    /**
     * Send the buy or production action to the client controller
     */
    public void sendPickedResources(){
        confirmChooseButton.setVisible(false);
        if(this.isProduction) {
            super.getClientController().produceResources(consumeTarget, productionCardsIDs, genericProduction, leaderProductions);
            this.productionCardsIDs.clear();
            this.leaderProductions.clear();
            this.genericProduction = Optional.empty();
        }
        else{
            super.getClientController().buyDevelopmentCard(cardToBuyId, chosenDeckIndex, consumeTarget);
        }
    }

    /**
     * Disable all resources and makes them not selectable anymore
     */
    public void disable(){
        SceneController.getMainGUIController().getStrongBoxController().setResourcesAsPickable(false);
        SceneController.getMainGUIController().getWarehouseController().setResourcesAsPickable(false);
        SceneController.getMainGUIController().getLeaderCardsController().setResourcesAsPickable(false);
    }

}
