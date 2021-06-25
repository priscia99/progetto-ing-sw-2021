package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientDevelopmentCardDecks;
import it.polimi.ingsw.client.model.ClientFaithPath;
import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import it.polimi.ingsw.client.model.ClientPlayerBoard;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Pair;

public class DevelopmentDeckView extends View implements Observer<Pair<ClientDevelopmentCardDecks, String>> {

    public DevelopmentDeckView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(Pair<ClientDevelopmentCardDecks, String> data) {
        userInterface.displayDevelopmentCardDecks(data.getFirst(), data.getSecond());
    }
}
