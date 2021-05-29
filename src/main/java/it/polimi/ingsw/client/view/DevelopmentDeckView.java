package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientPlayerBoard;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observer;

public class DevelopmentDeckView extends View implements Observer<ClientPlayerBoard> {

    protected DevelopmentDeckView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(ClientPlayerBoard object) {

    }
}
