package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientWarehouse;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Pair;

public class WarehouseView extends View implements Observer<Pair<ClientWarehouse, String>> {

    public WarehouseView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(Pair<ClientWarehouse, String> data) {
        userInterface.displayWarehouse(data.getFirst(), data.getSecond());
    }
}
