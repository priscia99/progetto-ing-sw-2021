package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientStrongbox;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.client.view.ui.gui.utils.FXHelper;
import it.polimi.ingsw.server.model.resource.ResourceType;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class StrongBoxController extends GenericGUIController {

    private GridPane strongboxPane;
    private TextField strongboxCoin, strongboxServant, strongboxShield, strongboxStone;
    private Pane strongboxCoinIco, strongboxServantIco, strongboxShieldIco, strongboxStoneIco;
    private boolean isManagingResources;
    private ClientStrongbox activeStrongbox;

    public StrongBoxController(ClientController clientController, GridPane strongboxPane, TextField strongboxCoin, TextField strongboxServant, TextField strongboxShield, TextField strongboxStone, Pane strongboxCoinIco, Pane strongboxServantIco, Pane strongboxShieldIco, Pane strongboxStoneIco) {
        super(clientController);
        this.strongboxPane = strongboxPane;
        this.strongboxCoin = strongboxCoin;
        this.strongboxServant = strongboxServant;
        this.strongboxShield = strongboxShield;
        this.strongboxStone = strongboxStone;
        this.strongboxCoinIco = strongboxCoinIco;
        this.strongboxServantIco = strongboxServantIco;
        this.strongboxShieldIco = strongboxShieldIco;
        this.strongboxStoneIco = strongboxStoneIco;
        this.isManagingResources = false;
    }

    /**
     * Refreshes strongbox pane with given strongbox object
     * @param strongbox Player's strongbox
     */
    public void refreshStrongbox(ClientStrongbox strongbox, boolean isMine){
        this.activeStrongbox = strongbox;
        if(strongbox.isInitialized()) {
            for(int i = 0; i<4; i++){
                switch (strongbox.getResourceStock(i).getResourceType()) {
                    case COIN -> strongboxCoin.setText(String.valueOf(strongbox.getResourceStock(i).getQuantity()));
                    case SERVANT -> strongboxServant.setText(String.valueOf(strongbox.getResourceStock(i).getQuantity()));
                    case SHIELD -> strongboxShield.setText(String.valueOf(strongbox.getResourceStock(i).getQuantity()));
                    case STONE -> strongboxStone.setText(String.valueOf(strongbox.getResourceStock(i).getQuantity()));
                }
            }
        }
        else{
            strongboxCoin.setText("0");
            strongboxServant.setText("0");
            strongboxShield.setText("0");
            strongboxStone.setText("0");
        }
        if(isMine)
            enableHandlers();
        else
            disableHandlers();
    }

    /**
     * Inits the strongbox screen by displaying a default strongbox with no resources inside
     */
    public void initStrongboxScreen(){
        strongboxCoin.setText("0");
        strongboxServant.setText("0");
        strongboxShield.setText("0");
        strongboxStone.setText("0");
    }

    /**
     * Makes all the resources selectable based on the parametrer given
     * @param isPickable set true is user can select the resources from the strongbox
     */
    public void setResourcesAsPickable(boolean isPickable){
        this.isManagingResources = isPickable;
        refreshStrongbox(activeStrongbox, true);
        if(isPickable) {
            strongboxPane.setStyle("-fx-border-color: darkred;");
            this.enableHandlers();
        }else{
            strongboxPane.setStyle("-fx-border: none;");
            this.disableHandlers();
        }

    }

    /**
     * Disable all the handlers for the strongbox icons
     */
    public void disableHandlers(){
        strongboxCoinIco.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        FXHelper.cleanEffects(strongboxCoinIco);
        FXHelper.cleanEffects(strongboxCoin);
        strongboxServantIco.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        FXHelper.cleanEffects(strongboxServantIco);
        FXHelper.cleanEffects(strongboxServant);
        strongboxShieldIco.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        FXHelper.cleanEffects(strongboxShieldIco);
        FXHelper.cleanEffects(strongboxShield);
        strongboxStoneIco.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        FXHelper.cleanEffects(strongboxStoneIco);
        FXHelper.cleanEffects(strongboxStone);
    }

    /**
     * Enable all the handlers for the strongbox icons
     */
    public void enableHandlers(){
        if(Integer.parseInt(strongboxCoin.getText())>0){
            strongboxCoinIco.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        }
        else{
            strongboxCoinIco.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        }

        if(Integer.parseInt(strongboxServant.getText())>0){
            strongboxServantIco.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        }
        else{
            strongboxServantIco.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        }

        if(Integer.parseInt(strongboxShield.getText())>0){
            strongboxShieldIco.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        }
        else{
            strongboxShieldIco.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        }

        if(Integer.parseInt(strongboxStone.getText())>0){
            strongboxStoneIco.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        }
        else{
            strongboxStoneIco.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        }
    }

    /**
     * Handler that is triggered when a specific resource is selected from the strongbox
     * This handler tells the controller that a specific resource is chosen from the strongbox
     */
    private final EventHandler<MouseEvent> onClickedResource = event -> {
        Pane triggeredPane = (Pane)event.getSource();
        ResourceType resourceType = null;
        switch (triggeredPane.getId()){
            case "coin-icon":       resourceType = ResourceType.COIN;
                                    strongboxCoin.setText(String.valueOf(Integer.parseInt(strongboxCoin.getText()) - 1));
                                    FXHelper.highlight(strongboxCoin);
                                    break;
            case "stone-icon":      resourceType = ResourceType.STONE;
                                    strongboxStone.setText(String.valueOf(Integer.parseInt(strongboxStone.getText()) - 1));
                                    FXHelper.highlight(strongboxStone);
                                    break;
            case "servant-icon":    resourceType = ResourceType.SERVANT;
                                    strongboxServant.setText(String.valueOf(Integer.parseInt(strongboxServant.getText()) - 1));
                                    FXHelper.highlight(strongboxServant);
                                    break;
            case "shield-icon":     resourceType = ResourceType.SHIELD;
                                    strongboxShield.setText(String.valueOf(Integer.parseInt(strongboxShield.getText()) - 1));
                                    FXHelper.highlight(strongboxShield);
                                    break;
        }
        this.enableHandlers();
        SceneController.getMainGUIController().getPickResourcesFromStorageController().addFromStrongbox(resourceType);
    };

}
