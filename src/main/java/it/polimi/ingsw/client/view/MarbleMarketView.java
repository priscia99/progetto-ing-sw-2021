package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientMarbleMarket;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observer;

public class MarbleMarketView extends View implements Observer<ClientMarbleMarket> {

    public MarbleMarketView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(ClientMarbleMarket object) {

    }
}
