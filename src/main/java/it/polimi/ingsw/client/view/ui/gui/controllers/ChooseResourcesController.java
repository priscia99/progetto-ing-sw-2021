package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.Map;

public class ChooseResourcesController {
    Map<String, Button> plusMinusButtons;
    AnchorPane chooseResourcesPane;
    Label chooseResourcesLabel;
    ClientController clientController;

    public ChooseResourcesController(AnchorPane chooseResourcesPane, Label chooseResourcesLabel, Map<String, Button> plusMinusButtons) {
        this.chooseResourcesPane = chooseResourcesPane;
        this.chooseResourcesLabel = chooseResourcesLabel;
        this.plusMinusButtons = plusMinusButtons;
    }

    public void activeScreen(int toChoose){
        if(toChoose == 1)
            chooseResourcesLabel.setText("CHOOSE " + toChoose + " RESOURCE");
        else
            chooseResourcesLabel.setText("CHOOSE " + toChoose + " RESOURCES");
        //chooseResourcesPane.setVisible(true);
    }

    public void setClientController(ClientController clientController){
        this.clientController = clientController;
    }
}
