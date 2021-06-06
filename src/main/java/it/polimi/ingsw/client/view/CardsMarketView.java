package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientCardsMarket;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observer;

public class CardsMarketView extends View implements Observer<ClientCardsMarket> {

    public CardsMarketView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(ClientCardsMarket market) {
        userInterface.displayCardMarket(market);
    }
}
