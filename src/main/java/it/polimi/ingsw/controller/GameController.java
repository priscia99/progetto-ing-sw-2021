package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

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

    public void doActionA(int positionX, int positionY, int deckIndex) {
        if(game.getCurrentPlayer().hasDoneMainAction()) throw new InvalidActionException();
        DevelopmentCard cardBought = game.getCardMarket().sell(positionX,positionY, game.getCurrentPlayer());
        game.getCurrentPlayer().getPlayerBoard().addDevelopmentCard(cardBought, deckIndex);
        game.getCurrentPlayer().setHasDoneMainAction(true);
    }
}
