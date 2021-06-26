package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientStrongbox;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
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
    }

    /**
     * Refreshes strongbox pane with given strongbox object
     * @param strongbox Player's strongbox
     */
    public void refreshStrongbox(ClientStrongbox strongbox){
        if(strongbox.isInitialized()) {
            strongboxCoin.setText(String.valueOf(strongbox.gerResourceStock(0).getQuantity()));
            strongboxServant.setText(String.valueOf(strongbox.gerResourceStock(1).getQuantity()));
            strongboxShield.setText(String.valueOf(strongbox.gerResourceStock(2).getQuantity()));
            strongboxStone.setText(String.valueOf(strongbox.gerResourceStock(3).getQuantity()));
        }
        else{
            strongboxCoin.setText("0");
            strongboxServant.setText("0");
            strongboxShield.setText("0");
            strongboxStone.setText("0");
        }
    }

    public void initStrongboxScreen(){
        strongboxCoin.setText("0");
        strongboxServant.setText("0");
        strongboxShield.setText("0");
        strongboxStone.setText("0");
    }

    public void setResourcesAsPickable(boolean isPickable){
        if(isPickable) {
            strongboxPane.setStyle("-fx-border-color: darkred;");
            strongboxCoin.setText("1");
            this.enableHandlers();
        }else{
            strongboxPane.setStyle("-fx-border: none;");
            this.disableHandlers();
        }

    }

    public void disableHandlers(){
        strongboxCoinIco.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        strongboxCoinIco.setEffect(null);
        strongboxServantIco.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        strongboxServantIco.setEffect(null);
        strongboxShieldIco.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        strongboxShieldIco.setEffect(null);
        strongboxStoneIco.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResource);
        strongboxStoneIco.setEffect(null);
    }

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

    private final EventHandler<MouseEvent> onClickedResource = event -> {
        Pane triggeredPane = (Pane)event.getSource();
        ResourceType resourceType = null;
        switch (triggeredPane.getId()){
            case "coin-icon":       resourceType = ResourceType.COIN;
                                    strongboxCoin.setText(String.valueOf(Integer.parseInt(strongboxCoin.getText()) - 1));
                                    strongboxCoin.setEffect(new Glow(0.6));
                                    break;
            case "stone-icon":      resourceType = ResourceType.STONE;
                                    strongboxStone.setText(String.valueOf(Integer.parseInt(strongboxStone.getText()) - 1));
                                    strongboxStone.setEffect(new Glow(0.6));
                                    break;
            case "servant-icon":    resourceType = ResourceType.SERVANT;
                                    strongboxServant.setText(String.valueOf(Integer.parseInt(strongboxServant.getText()) - 1));
                                    strongboxServant.setEffect(new Glow(0.6));
                                    break;
            case "shield-icon":     resourceType = ResourceType.SHIELD;
                                    strongboxShield.setText(String.valueOf(Integer.parseInt(strongboxShield.getText()) - 1));
                                    strongboxShield.setEffect(new Glow(0.6));
                                    break;
        }
        this.enableHandlers();
        SceneController.getMainGUIController().getPickResourcesFromStorageController().addFromStrongbox(resourceType);
    };

}
