package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientWarehouse;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observer;

public class WarehouseView extends View implements Observer<ClientWarehouse> {

    public WarehouseView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(ClientWarehouse warehouse) {
        userInterface.displayWarehouse(warehouse);
    }
}
