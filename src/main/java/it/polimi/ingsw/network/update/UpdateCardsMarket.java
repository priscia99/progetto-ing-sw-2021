package it.polimi.ingsw.network.update;

import it.polimi.ingsw.view.client_model.ClientCardsMarket;
import it.polimi.ingsw.model.market.CardsMarket;

public class UpdateCardsMarket extends Update{

    public UpdateCardsMarket(Object object) {
        super(new ClientCardsMarket(((CardsMarket) object)));
    }

    @Override
    public void execute() {
        // TODO fill me
    }
}
