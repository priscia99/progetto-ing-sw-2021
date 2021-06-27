package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import javafx.scene.layout.Pane;

public class ProductionController extends GenericGUIController{

    private Pane productionPane;

    public ProductionController(ClientController clientController, Pane productionPane) {
        super(clientController);
        this.productionPane = productionPane;
    }

    public void startProduction(){
        productionPane.setVisible(true);
    }

}
