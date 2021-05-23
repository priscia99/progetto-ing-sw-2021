package it.polimi.ingsw.network.update;

import it.polimi.ingsw.view.client_model.ClientLeaderCard;
import it.polimi.ingsw.model.player_board.LeaderCardsDeck;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class UpdateLeaderCards extends Update{

    public UpdateLeaderCards(Object object) {
        super(((LeaderCardsDeck) object).getLeaderCards()
                .stream()
                .map(ClientLeaderCard::new)
                .collect(Collectors.toCollection(ArrayList::new)));

    }

    @Override
    public void execute() {
        // TODO fill me
    }
}
