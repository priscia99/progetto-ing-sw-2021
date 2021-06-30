package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class PlayerBoardController extends GenericGUIController{
    private MenuButton playerSelector;
    private Label playerBoardLabel;

    public PlayerBoardController(ClientController clientController, MenuButton playerSelector, Label playerBoardLabel) {
        super(clientController);
        this.playerSelector = playerSelector;
        this.playerBoardLabel = playerBoardLabel;
    }

    /**
     * Initialize the playerboard choice picker
     * @param players
     */
    public void initClientSelector(ArrayList<String> players){
        players.stream().forEach(playerName -> playerSelector.getItems().add(new MenuItem(playerName)));
        for(MenuItem item : playerSelector.getItems()){
            item.setOnAction(onPlayerSelected);
        }
    }

    /**
     * Handler that is triggered when the user wants to view another player's playerboard
     * This handler tells the controller that the user wants to view a specific playerboard
     */
    EventHandler<ActionEvent> onPlayerSelected = event -> {
        super.getClientController().displayPlayerboardByUsername(((MenuItem)event.getSource()).getText());
    };

    /**
     * Sets the active playerboard username
     * @param username name of the player which playerboard is displayed
     * @param isMine set true is the player is the same as the window game player
     */
    public void setUsername(String username, boolean isMine){
            playerBoardLabel.setText(username + "'s PlayerBoard");
    }

    /**
     * Makes the playerboard selector disable
     */
    public void disableChangePlayer(){
        this.playerSelector.setDisable(true);
    }

    /**
     * Makes the playerboard selector enable
     */
    public void enableChangePlayer(){
        this.playerSelector.setDisable(false);
    }
}
