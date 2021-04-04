package it.polimi.ingsw;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.logging.Level;

public class App
{
    public static void main( String[] args )
    {
        final String gameId = "1";
        final int playerNumber = 2;
        Game game = new Game(gameId, playerNumber);
    }
}
