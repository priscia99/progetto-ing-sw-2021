package it.polimi.ingsw_old.network.message;

import it.polimi.ingsw_old.controller.Controller;
import it.polimi.ingsw_old.model.card.LeaderCard;

import java.util.ArrayList;

public class LeaderCardsMessage implements Message {

    private ArrayList<LeaderCard> leaderCards;

    public LeaderCardsMessage(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    @Override
    public void execute(Controller controller) {

    }
}
