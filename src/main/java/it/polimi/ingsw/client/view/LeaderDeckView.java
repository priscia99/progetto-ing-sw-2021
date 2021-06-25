package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import it.polimi.ingsw.client.model.ClientWarehouse;
import it.polimi.ingsw.client.view.representation.RepresentationBuilder;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Pair;

public class LeaderDeckView extends View implements Observer<Pair<ClientLeaderCardDeck, String>> {

    public LeaderDeckView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(Pair<ClientLeaderCardDeck, String> data) {
        userInterface.displayLeaderCardDeck(data.getFirst(), data.getSecond());
    }
}
