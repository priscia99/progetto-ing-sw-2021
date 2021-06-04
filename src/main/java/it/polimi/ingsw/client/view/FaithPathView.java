package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientFaithPath;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observer;

public class FaithPathView extends View implements Observer<ClientFaithPath> {

    public FaithPathView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(ClientFaithPath path) {
        userInterface.displayFaithPath(path);
    }
}
