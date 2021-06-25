package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class PlayerBoardController {
    private ClientController clientController;
    private MenuButton playerSelector;
    private Label playerBoardLabel;

    public PlayerBoardController(MenuButton playerSelector, Label playerBoardLabel) {
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

    EventHandler<ActionEvent> onPlayerSelected = event -> {
        clientController.displayPlayerboardByUsername(((MenuItem)event.getSource()).getText());
    };

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public void setUsername(String username, boolean isMine){
        if(isMine){
            playerBoardLabel.setText("Your PlayerBoard (" + username + ")");
        }else{
            playerBoardLabel.setText(username + "'s PlayerBoard");
        }
    }
}
