package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientWarehouse;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observer;

public class ErrorView extends View implements Observer<String> {

    public ErrorView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(String error) {
        userInterface.displayError(error);
    }
}
