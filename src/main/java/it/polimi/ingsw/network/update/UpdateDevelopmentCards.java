package it.polimi.ingsw.network.update;

import it.polimi.ingsw.view.client_model.ClientDevelopmentCard;
import it.polimi.ingsw.model.player_board.DevelopmentCardsDeck;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class UpdateDevelopmentCards extends Update{

    public UpdateDevelopmentCards(Object object) {
        super(((DevelopmentCardsDeck) object).getDeck()
                .stream()
                .map(ClientDevelopmentCard::new)
                .collect(Collectors.toCollection(ArrayList::new)));
    }

    @Override
    public void execute() {
        // TODO fill me
    }
}
