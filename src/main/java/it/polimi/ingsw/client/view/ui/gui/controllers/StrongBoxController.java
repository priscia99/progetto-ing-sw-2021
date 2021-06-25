package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientStrongbox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class StrongBoxController extends GenericGUIController {

    private GridPane strongboxPane;
    private TextField strongboxCoin, strongboxServant, strongboxShield, strongboxStone;

    public StrongBoxController(ClientController clientController, GridPane strongboxPane, TextField strongboxCoin, TextField strongboxServant, TextField strongboxShield, TextField strongboxStone) {
        super(clientController);
        this.strongboxPane = strongboxPane;
        this.strongboxCoin = strongboxCoin;
        this.strongboxServant = strongboxServant;
        this.strongboxShield = strongboxShield;
        this.strongboxStone = strongboxStone;
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

}
