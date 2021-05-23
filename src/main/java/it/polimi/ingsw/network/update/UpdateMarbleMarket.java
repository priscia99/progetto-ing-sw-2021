package it.polimi.ingsw.network.update;

import it.polimi.ingsw.view.client_model.ClientMarbleMarket;
import it.polimi.ingsw.model.market.MarbleMarket;

public class UpdateMarbleMarket extends Update{

    public UpdateMarbleMarket(Object object) {
        super(new ClientMarbleMarket((MarbleMarket) object));
    }

    @Override
    public void execute() {
        // TODO fill me
    }
}
