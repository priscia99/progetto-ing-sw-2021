package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import it.polimi.ingsw.client.view.representation.RepresentationBuilder;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observer;

public class LeaderDeckView extends View implements Observer<ClientLeaderCardDeck> {

    public LeaderDeckView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(ClientLeaderCardDeck deck) {
        userInterface.displayLeaderCardDeck(deck);
    }

    // TODO add owner check
}
