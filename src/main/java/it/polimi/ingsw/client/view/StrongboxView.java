package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import it.polimi.ingsw.client.model.ClientStrongbox;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Pair;

public class StrongboxView extends View implements Observer<Pair<ClientStrongbox, String>> {

    public StrongboxView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(Pair<ClientStrongbox, String> data) {
        userInterface.displayStrongBox(data.getFirst(), data.getSecond());
    }
}
