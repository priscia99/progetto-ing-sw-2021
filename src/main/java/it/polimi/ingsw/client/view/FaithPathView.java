package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientFaithPath;
import it.polimi.ingsw.client.model.ClientStrongbox;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Pair;

public class FaithPathView extends View implements Observer<Pair<ClientFaithPath, String>> {

    public FaithPathView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(Pair<ClientFaithPath, String> data) {
        userInterface.displayFaithPath(data.getFirst(), data.getSecond());
    }
}
