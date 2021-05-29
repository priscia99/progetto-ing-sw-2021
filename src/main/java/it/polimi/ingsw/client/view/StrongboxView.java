package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientStrongbox;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observer;

public class StrongboxView extends View implements Observer<ClientStrongbox> {

    public StrongboxView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(ClientStrongbox object) {

    }
}
