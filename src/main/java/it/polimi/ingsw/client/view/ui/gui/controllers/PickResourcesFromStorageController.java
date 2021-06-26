package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.server.model.resource.ConsumeTarget;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;

public class PickResourcesFromStorageController extends GenericGUIController{

    private Button confirmChooseButton;
    private ConsumeTarget consumeTarget;
    private boolean isProduction;
    private String cardToBuyId;
    private int chosenDeckIndex;

    public PickResourcesFromStorageController(ClientController controller, Button confirmChooseButton){
        super(controller);
        this.confirmChooseButton = confirmChooseButton;
        confirmChooseButton.setVisible(false);
    }

    public void enablePickResources(String cardToBuyId, int chosenDeckIndex){
        this.isProduction = false;
        this.cardToBuyId = cardToBuyId;
        this.chosenDeckIndex = chosenDeckIndex;

        consumeTarget = new ConsumeTarget();
        confirmChooseButton.setVisible(true);
        confirmChooseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedChooseButton);
        SceneController.getMainGUIController().getWarehouseController().setResourcesAsPickable(true);
        SceneController.getMainGUIController().getStrongBoxController().setResourcesAsPickable(true);
    }

    public void addFromStrongbox(ResourceType resourceType) {
        try {
            consumeTarget.put(ResourcePosition.STRONG_BOX, new ResourceStock(resourceType, 1));
        }catch (Exception e){
            super.getClientController().viewErrorMessage(e.getMessage());
        }
    }

    public void addFromWarehouse(ResourcePosition position, ResourceType resourceType){
        try{
            consumeTarget.put(position, new ResourceStock(resourceType, 1));
        }catch (Exception e){
            super.getClientController().viewErrorMessage(e.getMessage());
        }
    }

    private final EventHandler<javafx.scene.input.MouseEvent> onClickedChooseButton = event -> {
        this.disable();
        this.sendPickedResources();

    };

    public void sendPickedResources(){
        if(this.isProduction) {
            // send a production message
            // super.getClientController().
        }
        else{
            super.getClientController().buyDevelopmentCard(cardToBuyId, chosenDeckIndex, consumeTarget);
        }
    }

    public void disable(){
        SceneController.getMainGUIController().getStrongBoxController().setResourcesAsPickable(false);
        SceneController.getMainGUIController().getWarehouseController().setResourcesAsPickable(false);
    }

}
