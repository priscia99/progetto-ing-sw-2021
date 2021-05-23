package v2.server.controller;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import v2.server.controller.action.Action;

import java.util.ArrayList;

public class GameController {
    private Game game;

    public GameController(Game game){
        this.game = game;
    }

    public void setupGame(ArrayList<Player> players){
        game.setup(players);
    }

    public void executeAction(Action action){
        Game backup = new Game();
        try{
            backup = game.getBackup();
            action.execute(game);
        } catch(Exception ex){
            game.notifyError(ex);
            game.applyBackup(backup);
        }
    }

}
