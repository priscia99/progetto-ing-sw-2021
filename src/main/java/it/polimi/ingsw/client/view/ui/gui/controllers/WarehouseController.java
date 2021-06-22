package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.model.ClientWarehouse;
import javafx.scene.layout.GridPane;

public class WarehouseController {
    private static final String COIN_PATH = "/img/ico/coin.png";
    private static final String SERVANT_PATH = "/img/ico/servant.png";
    private static final String SHIELD_PATH = "/img/ico/shield.png";
    private static final String STONE_PATH = "/img/ico/stone.png";
    GridPane firstDepot;
    GridPane secondDepot;
    GridPane thirdDepot;

    public WarehouseController(GridPane firstDepot, GridPane secondDepot, GridPane thirdDepot) {
        this.firstDepot = firstDepot;
        this.secondDepot = secondDepot;
        this.thirdDepot = thirdDepot;
    }

    public void refreshWarehouse(ClientWarehouse warehouse){
        // FIXME warehouse
    }
}
