package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observer;

public class LeaderDeckView extends View implements Observer<ClientLeaderCardDeck> {

    protected LeaderDeckView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(ClientLeaderCardDeck deck) {
        // FIXME fix constructors
        /*
        object.getClientLeaderCards().stream().map(clientLeaderCard -> new LeaderCardRepresentation(
                clientLeaderCard.getId(),
                clientLeaderCard.getRequirement(),
                clientLeaderCard.getEffect(),
                clientLeaderCard.getVictoryPoints(),
                clientLeaderCard.isActive()
        ));
         */
    }
}
