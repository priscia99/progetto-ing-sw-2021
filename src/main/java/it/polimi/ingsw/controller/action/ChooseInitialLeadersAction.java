package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.game.Game;

import java.util.ArrayList;

public class ChooseInitialLeadersAction extends Action {
    private final ArrayList<LeaderCard> leadersChosen;
    private final String playerUsername;

    public ChooseInitialLeadersAction(ArrayList<LeaderCard> leaders, String username) {
        this.leadersChosen = leaders;
        this.playerUsername = username;
    }

    @Override
    public void execute(Game game) {
        game.getPlayerByUsername(playerUsername).pickedLeaderCards(leadersChosen);
        game.tryStart();
    }

}
