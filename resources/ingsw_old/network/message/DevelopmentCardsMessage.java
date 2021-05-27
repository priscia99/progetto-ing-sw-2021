package it.polimi.ingsw_old.network.message;

import it.polimi.ingsw_old.controller.Controller;
import it.polimi.ingsw_old.model.card.DevelopmentCard;

import java.util.ArrayList;

public class DevelopmentCardsMessage implements Message {

    private ArrayList<DevelopmentCard> developmentCards;

    //    ((DevelopmentCardsDeck) object).getDeck()
    //                .stream()
    //                .map(ClientDevelopmentCard::new)
    //                .collect(Collectors.toCollection(ArrayList::new))
    public DevelopmentCardsMessage(ArrayList<DevelopmentCard> developmentCards) {
        this.developmentCards = developmentCards;
    }

    @Override
    public void execute(Controller controller) {

    }
}
