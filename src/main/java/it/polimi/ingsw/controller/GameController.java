package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.server_model.card.LeaderCard;
import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.server_model.game.Player;
import it.polimi.ingsw.server_model.resource.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Game game;

    public GameController(Game game){
        this.game = game;
    }

    public void setupGame(ArrayList<Player> players){
        game.setup(players);
    }

    public void executeAction(Action action){
        action.execute(game);
    }

}
