package it.polimi.ingsw_old.network.message;

import it.polimi.ingsw_old.controller.Controller;
import it.polimi.ingsw_old.model.card.DevelopmentCard;

import java.io.Serializable;
import java.util.ArrayList;

public class CardsMarketMessage implements Message, Serializable {

    private final ArrayList<DevelopmentCard> developmentCards;

    public CardsMarketMessage(ArrayList<DevelopmentCard> developmentCards) {
        this.developmentCards = developmentCards;
    }

    @Override
    public void execute(Controller controller) {

    }
}
